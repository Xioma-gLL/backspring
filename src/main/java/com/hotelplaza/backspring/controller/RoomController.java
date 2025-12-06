package com.hotelplaza.backspring.controller;

import com.hotelplaza.backspring.dto.AvailableRoomDTO;
import com.hotelplaza.backspring.dto.RoomSearchRequest;
import com.hotelplaza.backspring.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = {"http://localhost:5173", "https://plazatrujillo.netlify.app", "https://hotelplazatrujillo.netlify.app"})
@RequiredArgsConstructor
public class RoomController {
    
    private final RoomService roomService;
    
    @PostMapping("/search")
    public ResponseEntity<List<AvailableRoomDTO>> searchAvailableRooms(@RequestBody RoomSearchRequest request) {
        List<AvailableRoomDTO> availableRooms = roomService.searchAvailableRooms(request);
        return ResponseEntity.ok(availableRooms);
    }
}
