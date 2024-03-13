package com.spring_rest.demo.dto.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReservationRequestDTO {
    @NotNull(message = "L'ID de la salle ne peut pas être nul")
    @Min(value = 1, message = "L'ID de la salle doit être supérieur ou égal à 1")
    private Long roomId;

    @NotNull(message = "L'heure et la date de début de la réservation ne peut pas être nulle")
    @FutureOrPresent(message = "L'heure de début de la réservation doit être dans le futur ou le présent")
    private LocalDateTime startTime;

    @NotNull(message = "L'heure et la date de fin de la réservation ne peut pas être nulle")
    @FutureOrPresent(message = "L'heure de fin de la réservation doit être dans le futur ou le présent")
    private LocalDateTime endTime;

    @NotEmpty(message = "L'utilisateur de la réservation ne peut pas être vide")
    private String user;
}
