package com.springApp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {
    @NotBlank(message = "El nombre del aula es requerido")
    private String name;

    @NotNull(message = "La capacidad es requerida")
    private Integer ability;

    @NotBlank(message = "El edificio es requerido")
    private String building;
}
