package com.spring_rest.demo.services;

import com.spring_rest.demo.dto.requests.RoomRequestDTO;
import com.spring_rest.demo.dto.responses.RoomResponseDTO;
import com.spring_rest.demo.exceptions.RoomNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {
    List<RoomResponseDTO> getAllRooms();
    public List<RoomResponseDTO> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime);
    RoomResponseDTO getRoomById(Long id);
    void createRoom(RoomRequestDTO roomDTO);
    void updateRoom(Long id, RoomRequestDTO roomDTO) throws RoomNotFoundException;
    void deleteRoom(Long id);

}
