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
public class SubjectDTO {
    @NotBlank(message = "El código de materia es requerido")
    private String subjectCode;

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotNull(message = "Las unidades valorativas son requeridas")
    private Integer valueUnits;

    @NotNull(message = "El ID de la carrera es requerido")
    private Long careerId;
}
