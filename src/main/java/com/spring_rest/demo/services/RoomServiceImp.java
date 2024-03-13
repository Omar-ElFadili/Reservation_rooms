package com.spring_rest.demo.services;

import com.spring_rest.demo.dto.requests.RoomRequestDTO;
import com.spring_rest.demo.dto.responses.RoomResponseDTO;
import com.spring_rest.demo.entities.Room;
import com.spring_rest.demo.exceptions.RoomNotFoundException;
import com.spring_rest.demo.mapper.RoomMapper;
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
public class RoomServiceImp implements RoomService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<RoomResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(RoomMapper.INSTANCE::roomToRoomDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<RoomResponseDTO> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        List<Room> allRooms = roomRepository.findAll();
        List<Long> occupiedRoomIds = reservationRepository
                .findRoomIdsReservedDuringInterval(startTime, endTime);

        List<Room> availableRooms = allRooms.stream()
                .filter(room -> !occupiedRoomIds.contains(room.getId()))
                .collect(Collectors.toList());

        return availableRooms.stream()
                .map(RoomMapper.INSTANCE::roomToRoomDTO)
                .collect(Collectors.toList());
    }
    @Override
    public RoomResponseDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
        return RoomMapper.INSTANCE.roomToRoomDTO(room);
    }

    @Override
    public void createRoom(RoomRequestDTO roomDTO) {
        Room room = RoomMapper.INSTANCE.roomDTOToRoom(roomDTO);
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(Long id, RoomRequestDTO roomDTO) throws RoomNotFoundException {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setName(roomDTO.getName());
            room.setCapacity(roomDTO.getCapacity());
            room.setDescription(roomDTO.getDescription());
            roomRepository.save(room);
        } else {
            throw new RoomNotFoundException("Writer not found with id: " +id);
        }
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }
}
