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
public class CareerDTO {
    @NotBlank(message = "El nombre de la carrera es requerido")
    private String nameCareer;

    private Integer plan;

    @NotBlank(message = "La facultad es requerida")
    private String faculty;
}

