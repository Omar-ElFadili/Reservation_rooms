package com.spring_rest.demo.controllers;

import com.spring_rest.demo.dto.requests.RoomRequestDTO;
import com.spring_rest.demo.dto.responses.RoomResponseDTO;
import com.spring_rest.demo.exceptions.RoomNotFoundException;
import com.spring_rest.demo.services.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        List<RoomResponseDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
    @GetMapping("/available")
    public ResponseEntity<List<RoomResponseDTO>> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {

        List<RoomResponseDTO> availableRooms = roomService.getAvailableRooms(startTime, endTime);

        return ResponseEntity.ok(availableRooms);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable Long id) {
        RoomResponseDTO room = roomService.getRoomById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody @Valid RoomRequestDTO roomDTO) {
        roomService.createRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Room has been created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomRequestDTO roomDTO) throws RoomNotFoundException {
        roomService.updateRoom(id, roomDTO);
        return ResponseEntity.ok("Room has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room has been deleted");
    }
}

