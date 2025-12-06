package com.hotelplaza.backspring.service;

import com.hotelplaza.backspring.dto.AvailableRoomDTO;
import com.hotelplaza.backspring.dto.RoomSearchRequest;
import com.hotelplaza.backspring.entity.Room;
import com.hotelplaza.backspring.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    
    private final RoomRepository roomRepository;
    
    public List<AvailableRoomDTO> searchAvailableRooms(RoomSearchRequest request) {
        List<Room> availableRooms = roomRepository.findAvailableRooms(
            request.getCheckIn(),
            request.getCheckOut()
        );
        
        // Convertir a DTO con datos básicos de la tabla rooms
        return availableRooms.stream()
            .map(room -> {
                // Asignar precio y capacidad por defecto según tipo
                String tipo = room.getType() != null ? room.getType() : "Simple";
                double precio = getPrecioPorTipo(tipo);
                int capacidad = getCapacidadPorTipo(tipo);
                
                return new AvailableRoomDTO(
                    room.getCode(),
                    tipo,
                    room.getFloor(),
                    room.getCapacity() != null ? room.getCapacity() : capacidad,
                    room.getPrice() != null ? room.getPrice().doubleValue() : precio,
                    room.getStatus(),
                    getDescripcionPorTipo(tipo),
                    room.getImageUrl() != null ? room.getImageUrl() : getImagenPorTipo(tipo)
                );
            })
            .collect(Collectors.toList());
    }
    
    private double getPrecioPorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "s" -> 100.00;      // Simple
            case "m" -> 120.00;      // Matrimonial
            case "d" -> 130.00;      // Doble
            case "df" -> 150.00;     // Doble Familiar
            case "t" -> 180.00;      // Triple
            case "tr" -> 200.00;     // Triple
            default -> 100.00;
        };
    }
    
    private int getCapacidadPorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "s" -> 2;      // Simple - 2 personas
            case "m" -> 2;      // Matrimonial - 2 personas
            case "d" -> 2;      // Doble - 2 personas
            case "df" -> 3;     // Doble Familiar - 3 personas
            case "t" -> 4;      // Triple - 4 personas
            case "tr" -> 4;     // Triple - 4 personas
            default -> 2;
        };
    }
    
    private String getDescripcionPorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "s" -> "Habitación simple para 2 personas";
            case "m" -> "Habitación matrimonial con cama queen para 2 personas";
            case "d" -> "Habitación doble con 2 camas para 2 personas";
            case "df" -> "Habitación doble familiar para 3 personas";
            case "t", "tr" -> "Habitación triple para 4 personas";
            default -> "Habitación estándar";
        };
    }
    
    private String getImagenPorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "s" -> "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600&q=80";
            case "m" -> "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=600&q=80";
            case "d" -> "https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=600&q=80";
            case "df" -> "https://images.unsplash.com/photo-1595576508898-0ad5c879a061?w=600&q=80";
            case "t", "tr" -> "https://images.unsplash.com/photo-1611892440504-42a792e24d32?w=600&q=80";
            default -> "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600&q=80";
        };
    }
}
