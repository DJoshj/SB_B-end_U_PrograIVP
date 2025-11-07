package com.springApp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    @NotBlank(message = "Los días son requeridos")
    private String days;

    @NotBlank(message = "El horario es requerido")
    private String schedule;
}
