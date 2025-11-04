package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssignedDTO {

    @NotNull(message = "El ID de la materia es requerido")
    @JsonProperty("subjectId")
    private Long subjectId;

    @NotNull(message = "El ID del docente es requerido")
    @JsonProperty("teacherId")
    private Long teacherId;

    @NotNull(message = "El ID del periodo es requerido")
    @JsonProperty("periodId")
    private Long periodId;

    @NotNull(message = "El ID del horario es requerido")
    @JsonProperty("scheduleId")
    private Long scheduleId;

    @NotNull(message = "El ID del aula es requerido")
    @JsonProperty("classroomId")
    private Long classroomId;

    @NotNull(message = "La capacidad máxima es requerida")
    @Positive(message = "La capacidad debe ser mayor a 0")
    @JsonProperty("maximumCapacity")
    private Integer maximumCapacity;

    @NotNull(message = "La sección es requerida")
    @JsonProperty("section")
    private String section;
}
