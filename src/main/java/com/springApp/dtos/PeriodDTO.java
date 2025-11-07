package com.springApp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodDTO {
    @NotBlank(message = "El nombre del periodo es requerido")
    private String name;

    @NotNull(message = "El año es requerido")
    private Integer year;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotBlank(message = "El horario es requerido")
    private String schedule;
}
