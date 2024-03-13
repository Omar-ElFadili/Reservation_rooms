package com.spring_rest.demo.mapper;

import com.spring_rest.demo.dto.requests.ReservationRequestDTO;
import com.spring_rest.demo.dto.responses.ReservationResponseDTO;
import com.spring_rest.demo.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationResponseDTO reservationToReservationDTO(Reservation reservation);

    Reservation reservationDTOToReservation(ReservationRequestDTO reservationDTO);
}