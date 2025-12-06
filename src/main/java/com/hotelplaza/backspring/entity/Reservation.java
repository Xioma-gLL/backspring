package com.hotelplaza.backspring.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reservation_id", unique = true, length = 20)
    private String reservationId;
    
    @Column(length = 50)
    private String channel;
    
    @Column(name = "guest_name", length = 200)
    private String guestName;
    
    @Column(name = "room_label", length = 100)
    private String roomLabel;
    
    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;
    
    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;
    
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @Column(length = 50)
    private String status = "Confirmada";
    
    private Boolean paid = false;
    
    @Column(name = "document_type", length = 10)
    private String documentType;
    
    @Column(name = "document_number", length = 20)
    private String documentNumber;
    
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    
    @Column(name = "departure_time")
    private LocalTime departureTime;
    
    @Column(name = "num_people")
    private Integer numPeople = 1;
    
    @Column(name = "num_adults")
    private Integer numAdults = 1;
    
    @Column(name = "num_children")
    private Integer numChildren = 0;
    
    @Column(name = "num_rooms")
    private Integer numRooms = 1;
    
    @Column(length = 300)
    private String address;
    
    @Column(length = 100)
    private String department;
    
    @Column(length = 100)
    private String province;
    
    @Column(length = 100)
    private String district;
    
    @Column(name = "room_type", length = 20)
    private String roomType;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @Column(length = 20)
    private String sex;
    
    @Column(name = "taxpayer_type", length = 100)
    private String taxpayerType;
    
    @Column(name = "business_status", length = 50)
    private String businessStatus;
    
    @Column(name = "business_condition", length = 50)
    private String businessCondition;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (reservationId == null || reservationId.isEmpty()) {
            // Se generará después del insert
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
