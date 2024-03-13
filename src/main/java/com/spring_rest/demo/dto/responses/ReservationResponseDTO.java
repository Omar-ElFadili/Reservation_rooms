package com.spring_rest.demo.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReservationResponseDTO {
    private Long id;
    private Long roomId;
    private String user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
