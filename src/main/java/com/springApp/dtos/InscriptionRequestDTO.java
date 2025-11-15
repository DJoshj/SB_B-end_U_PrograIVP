package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO para inscribir un estudiante
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionRequestDTO {
    @NotNull(message = "El ID del estudiante es requerido")
    @JsonProperty("studentId")
    private Long studentId;

    @NotNull(message = "El ID de la materia asignada es requerido")
    @JsonProperty("subjectsAssigned")
    private List<Long> subjectsAssigned; // lista de IDs de materias asignadas

    @NotNull(message = "El ID del periodo es requerido")
    @JsonProperty("periodId")
    private Long periodId;

    /*
    el JSON del request sería algo como:
    {
      "studentId": 10,
      "periodId": 3,
      "subjectsAssigned": [5, 7, 9]
    }

    * */
}
