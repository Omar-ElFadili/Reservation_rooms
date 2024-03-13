package com.spring_rest.demo.mapper;

import com.spring_rest.demo.dto.requests.RoomRequestDTO;
import com.spring_rest.demo.dto.responses.RoomResponseDTO;
import com.spring_rest.demo.entities.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomResponseDTO roomToRoomDTO(Room room);

    Room roomDTOToRoom(RoomRequestDTO roomDTO);
}
