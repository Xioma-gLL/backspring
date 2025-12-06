package com.hotelplaza.backspring.repository;

import com.hotelplaza.backspring.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    // Buscar habitaciones disponibles en un rango de fechas
    @Query("SELECT r FROM Room r WHERE r.status = 'Disponible' " +
           "AND r.code NOT IN (" +
           "  SELECT res.roomLabel FROM Reservation res " +
           "  WHERE res.status IN ('Confirmada', 'Check-in') " +
           "  AND ((res.checkIn <= :checkOut AND res.checkOut > :checkIn))" +
           ")")
    List<Room> findAvailableRooms(
        @Param("checkIn") LocalDate checkIn, 
        @Param("checkOut") LocalDate checkOut
    );
}
