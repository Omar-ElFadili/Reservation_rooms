package com.spring_rest.demo.services;

import com.spring_rest.demo.dto.requests.ReservationRequestDTO;
import com.spring_rest.demo.dto.responses.ReservationResponseDTO;
import com.spring_rest.demo.entities.Reservation;
import com.spring_rest.demo.entities.Room;
import com.spring_rest.demo.exceptions.ReservationNotFoundException;
import com.spring_rest.demo.exceptions.RoomNotFoundException;
import com.spring_rest.demo.mapper.ReservationMapper;
import com.spring_rest.demo.repositories.ReservationRepository;
import com.spring_rest.demo.repositories.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImp implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationMapper.INSTANCE::reservationToReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        return ReservationMapper.INSTANCE.reservationToReservationDTO(reservation);
    }

    @Override
    public void createReservation(ReservationRequestDTO reservationDTO) {
        // Vérifier si la salle existe dans la base de données
        Optional<Room> optionalRoom = roomRepository.findById(reservationDTO.getRoomId());
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            LocalDateTime startTime = reservationDTO.getStartTime();
            LocalDateTime endTime = reservationDTO.getEndTime();
            // Vérifier si la salle est disponible à la date spécifiée
            if (isRoomAvailable(room, startTime, endTime)) {
                Reservation reservation = new Reservation();
                reservation.setRoom(room);
                reservation.setUser(reservationDTO.getUser());
                reservation.setStartTime(startTime);
                reservation.setEndTime(endTime);
                reservationRepository.save(reservation);
            } else {
                throw new RuntimeException("La salle est déjà réservée pour cette période.");
            }
        } else {
            throw new RoomNotFoundException("La salle avec l'ID " + reservationDTO.getRoomId() + " n'existe pas.");
        }
    }

    private boolean isRoomAvailable(Room room, LocalDateTime startTime, LocalDateTime endTime) {
        // Vérifier s'il y a des réservations pour cette salle pendant la période spécifiée
        List<Reservation> existingReservations = reservationRepository.findByRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                room, endTime, startTime);
        return existingReservations.isEmpty();
    }


    @Override
    public void updateReservation(Long id, ReservationRequestDTO reservationDTO) throws ReservationNotFoundException, RoomNotFoundException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setEndTime(reservationDTO.getEndTime());
            reservation.setStartTime(reservationDTO.getStartTime());
            reservation.setUser(reservationDTO.getUser());
            // Vérifier si la salle existe avant de l'assigner à la réservation
            Optional<Room> optionalRoom = roomRepository.findById(reservationDTO.getRoomId());
            if (optionalRoom.isPresent()) {
                Room room = optionalRoom.get();
                reservation.setRoom(room);
            } else {
                throw new RoomNotFoundException("Room not found with id: " + reservationDTO.getRoomId());
            }
            reservationRepository.save(reservation);
        } else {
            throw new ReservationNotFoundException("Reservation not found with id: " + id);
        }
    }


    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found with this id: " + id);
        }
        reservationRepository.deleteById(id);
    }
}
