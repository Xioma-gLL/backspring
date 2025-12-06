package com.hotelplaza.backspring.repository;

import com.hotelplaza.backspring.entity.WebReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebReservationRequestRepository extends JpaRepository<WebReservationRequest, Long> {
    
    List<WebReservationRequest> findByStatusOrderByCreatedAtDesc(String status);
    
    List<WebReservationRequest> findTop50ByOrderByCreatedAtDesc();
    
    List<WebReservationRequest> findByEmailOrderByCreatedAtDesc(String email);
}
