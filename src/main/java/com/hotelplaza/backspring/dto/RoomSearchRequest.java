package com.hotelplaza.backspring.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RoomSearchRequest {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer guests;
    private Integer children;
    private Integer rooms;
}
