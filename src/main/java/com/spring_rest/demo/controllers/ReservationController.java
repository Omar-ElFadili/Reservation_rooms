package com.spring_rest.demo.controllers;

import com.spring_rest.demo.dto.requests.ReservationRequestDTO;
import com.spring_rest.demo.dto.responses.ReservationResponseDTO;
import com.spring_rest.demo.exceptions.ReservationNotFoundException;
import com.spring_rest.demo.exceptions.RoomNotFoundException;
import com.spring_rest.demo.services.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        ReservationResponseDTO reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody @Valid ReservationRequestDTO reservationDTO) {
        reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Reservation has been created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable Long id, @RequestBody @Valid ReservationRequestDTO reservationDTO) throws RoomNotFoundException, ReservationNotFoundException {
        reservationService.updateReservation(id, reservationDTO);
        return ResponseEntity.ok("Reservation has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation has been deleted");
    }
}
