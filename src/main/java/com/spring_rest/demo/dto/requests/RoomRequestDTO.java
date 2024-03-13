package com.spring_rest.demo.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoomRequestDTO {
    @NotBlank(message = "Le nom de la salle ne peut pas être vide")
    @Size(min = 4, max = 255, message = "Le nom de la salle doit avoir entre {min} et {max} caractères")
    private String name;

    @NotBlank(message = "La description de la salle ne peut pas être vide")
    private String description;

    @NotNull(message = "La capacité de la salle ne peut pas être nulle")
    private Integer capacity;
}
