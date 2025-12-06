package com.hotelplaza.backspring.controller;

import com.hotelplaza.backspring.dto.WebReservationRequestDTO;
import com.hotelplaza.backspring.entity.WebReservationRequest;
import com.hotelplaza.backspring.repository.WebReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/web-reservation-requests")
@CrossOrigin(origins = "*")
public class WebReservationRequestController {
    
    @Autowired
    private WebReservationRequestRepository repository;
    
    /**
     * Obtener solicitudes por email del cliente (para "Mis Reservas")
     * Endpoint público
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<Map<String, Object>> getRequestsByEmail(@PathVariable String email) {
        try {
            List<WebReservationRequest> requests = repository.findByEmailOrderByCreatedAtDesc(email);
            
            Map<String, Object> response = new HashMap<>();
            response.put("requests", requests);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al obtener solicitudes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Endpoint PÚBLICO para crear solicitud de reserva desde el sitio web
     * NO requiere autenticación
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createRequest(@RequestBody WebReservationRequestDTO dto) {
        try {
            System.out.println("Recibiendo solicitud de reserva web: " + dto.getGuestName());
            
            WebReservationRequest request = new WebReservationRequest();
            request.setDocumentType(dto.getDocumentType());
            request.setDocumentNumber(dto.getDocumentNumber());
            request.setGuestName(dto.getGuestName());
            request.setPhone(dto.getPhone());
            request.setEmail(dto.getEmail());
            request.setAddress(dto.getAddress());
            request.setDepartment(dto.getDepartment());
            request.setProvince(dto.getProvince());
            request.setDistrict(dto.getDistrict());
            request.setChannel(dto.getChannel() != null ? dto.getChannel() : "Web");
            request.setCheckIn(dto.getCheckIn());
            request.setCheckOut(dto.getCheckOut());
            request.setNumAdults(dto.getNumAdults());
            request.setNumChildren(dto.getNumChildren() != null ? dto.getNumChildren() : 0);
            request.setNumPeople(dto.getNumPeople());
            request.setNumRooms(dto.getNumRooms());
            request.setArrivalTime(dto.getArrivalTime());
            request.setDepartureTime(dto.getDepartureTime());
            request.setTotalAmount(dto.getTotalAmount());
            request.setRooms(dto.getRooms());
            request.setRoomType(dto.getRoomType());
            request.setStatus("pending");
            
            WebReservationRequest saved = repository.save(request);
            System.out.println("Reserva web guardada con ID: " + saved.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Solicitud de reserva creada exitosamente");
            response.put("requestId", saved.getId());
            response.put("data", saved);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error al crear solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Listar solicitudes de reserva (requiere autenticación en producción)
     * Automáticamente cambia "pending" a "received" cuando el dashboard las consulta
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listRequests(
            @RequestParam(required = false, defaultValue = "pending") String status) {
        try {
            List<WebReservationRequest> requests;
            
            // Si pide "pending", incluir también "received" para mostrar ambas
            if ("pending".equals(status)) {
                List<WebReservationRequest> pendingRequests = repository.findByStatusOrderByCreatedAtDesc("pending");
                List<WebReservationRequest> receivedRequests = repository.findByStatusOrderByCreatedAtDesc("received");
                
                // Cambiar automáticamente las pendientes a "received"
                for (WebReservationRequest req : pendingRequests) {
                    req.setStatus("received");
                    repository.save(req);
                }
                
                // Combinar ambas listas
                requests = new java.util.ArrayList<>();
                requests.addAll(pendingRequests);
                requests.addAll(receivedRequests);
                
                // Ordenar por fecha de creación descendente
                requests.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
            } else if ("all".equals(status)) {
                requests = repository.findTop50ByOrderByCreatedAtDesc();
            } else {
                requests = repository.findByStatusOrderByCreatedAtDesc(status);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("requests", requests);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al listar solicitudes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Obtener una solicitud específica
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRequest(@PathVariable Long id) {
        try {
            return repository.findById(id)
                    .map(request -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("request", request);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Solicitud no encontrada");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                    });
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al obtener solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Actualizar estado de una solicitud
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRequest(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        try {
            return repository.findById(id)
                    .map(request -> {
                        if (updates.containsKey("status")) {
                            String newStatus = (String) updates.get("status");
                            // "processed" se convierte en "registered"
                            if ("processed".equals(newStatus)) {
                                request.setStatus("registered");
                                request.setProcessedAt(LocalDateTime.now());
                                if (updates.containsKey("processedBy")) {
                                    request.setProcessedBy((String) updates.get("processedBy"));
                                }
                            } else {
                                request.setStatus(newStatus);
                            }
                        }
                        
                        if (updates.containsKey("notes")) {
                            request.setNotes((String) updates.get("notes"));
                        }
                        
                        WebReservationRequest saved = repository.save(request);
                        
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", saved);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Solicitud no encontrada");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                    });
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al actualizar solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
