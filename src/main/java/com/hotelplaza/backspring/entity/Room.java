package com.hotelplaza.backspring.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Data
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 10)
    private String code;
    
    @Column(nullable = false)
    private Integer floor;
    
    @Column(length = 10)
    private String type;
    
    @Column(nullable = false, length = 50)
    private String status = "Disponible";
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
}
