package com.spring_rest.demo.dto.responses;

import lombok.Data;

@Data
public class RoomResponseDTO {
    private Long id;
    private String name;
    private int capacity;
    private String description;
}
