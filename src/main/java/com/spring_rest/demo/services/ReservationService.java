package com.spring_rest.demo.services;

import com.spring_rest.demo.dto.requests.ReservationRequestDTO;
import com.spring_rest.demo.dto.responses.ReservationResponseDTO;
import com.spring_rest.demo.exceptions.ReservationNotFoundException;
import com.spring_rest.demo.exceptions.RoomNotFoundException;

import java.util.List;

public interface ReservationService {
    List<ReservationResponseDTO> getAllReservations();
    ReservationResponseDTO getReservationById(Long id);
    void createReservation(ReservationRequestDTO reservationDTO);
    void updateReservation(Long id, ReservationRequestDTO reservationDTO) throws ReservationNotFoundException, RoomNotFoundException;
    void deleteReservation(Long id);
}