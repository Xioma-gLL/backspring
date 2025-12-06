package com.hotelplaza.backspring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class WebReservationRequestDTO {
    
    // Datos del cliente
    private String documentType;
    private String documentNumber;
    private String guestName;
    private String phone;
    private String email;
    private String address;
    private String department;
    private String province;
    private String district;
    
    // Datos de la reserva
    private String channel;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer numAdults;
    private Integer numChildren;
    private Integer numPeople;
    private Integer numRooms;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private BigDecimal totalAmount;
    private String rooms;
    private String roomType;
    
    // Getters y Setters
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
}
