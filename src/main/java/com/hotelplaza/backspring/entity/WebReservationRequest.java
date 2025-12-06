package com.hotelplaza.backspring.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "web_reservation_requests")
public class WebReservationRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Datos del cliente
    @Column(name = "document_type", length = 20)
    private String documentType;
    
    @Column(name = "document_number", length = 20)
    private String documentNumber;
    
    @Column(name = "guest_name")
    private String guestName;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 255)
    private String email;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @Column(length = 100)
    private String department;
    
    @Column(length = 100)
    private String province;
    
    @Column(length = 100)
    private String district;
    
    // Datos de la reserva
    @Column(length = 50)
    private String channel = "Web";
    
    @Column(name = "check_in")
    private LocalDate checkIn;
    
    @Column(name = "check_out")
    private LocalDate checkOut;
    
    @Column(name = "num_adults")
    private Integer numAdults;
    
    @Column(name = "num_children")
    private Integer numChildren = 0;
    
    @Column(name = "num_people")
    private Integer numPeople;
    
    @Column(name = "num_rooms")
    private Integer numRooms;
    
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    
    @Column(name = "departure_time")
    private LocalTime departureTime;
    
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(columnDefinition = "TEXT")
    private String rooms; // Códigos de habitaciones separados por coma
    
    @Column(name = "room_type", length = 100)
    private String roomType;
    
    // Estado y metadatos
    @Column(length = 20)
    private String status = "pending"; // pending, processed, rejected
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    @Column(name = "processed_by")
    private String processedBy;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
    public String getDocumentNumber() {
        return documentNumber;
    }
    
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    public String getGuestName() {
        return guestName;
    }
    
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getChannel() {
        return channel;
    }
    
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public LocalDate getCheckIn() {
        return checkIn;
    }
    
    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }
    
    public LocalDate getCheckOut() {
        return checkOut;
    }
    
    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
    
    public Integer getNumAdults() {
        return numAdults;
    }
    
    public void setNumAdults(Integer numAdults) {
        this.numAdults = numAdults;
    }
    
    public Integer getNumChildren() {
        return numChildren;
    }
    
    public void setNumChildren(Integer numChildren) {
        this.numChildren = numChildren;
    }
    
    public Integer getNumPeople() {
        return numPeople;
    }
    
    public void setNumPeople(Integer numPeople) {
        this.numPeople = numPeople;
    }
    
    public Integer getNumRooms() {
        return numRooms;
    }
    
    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }
    
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public LocalTime getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getRooms() {
        return rooms;
    }
    
    public void setRooms(String rooms) {
        this.rooms = rooms;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
    
    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
    
    public String getProcessedBy() {
        return processedBy;
    }
    
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
