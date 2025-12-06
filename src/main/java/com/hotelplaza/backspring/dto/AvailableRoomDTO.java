package com.hotelplaza.backspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRoomDTO {
    private String code;
    private String type;
    private Integer floor;
    private Integer capacity;
    private Double price;
    private String status;
    private String description;
    private String imageUrl;
}
