package com.spring_rest.demo.repositories;

import com.spring_rest.demo.entities.Reservation;
import com.spring_rest.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r.room.id FROM Reservation r WHERE (r.startTime BETWEEN :startTime AND :endTime) OR (r.endTime BETWEEN :startTime AND :endTime)")
    List<Long> findRoomIdsReservedDuringInterval(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    @Query("SELECT r FROM Reservation r WHERE r.room = :room AND r.startTime <= :endTime AND r.endTime >= :startTime")
    List<Reservation> findByRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Room room, LocalDateTime endTime, LocalDateTime startTime);
}
