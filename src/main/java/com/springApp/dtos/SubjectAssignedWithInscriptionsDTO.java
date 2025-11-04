package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO adicional para ver inscripciones en SubjectAssignedResponseDTO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssignedWithInscriptionsDTO {
    // Información básica de la materia asignada
    @JsonProperty("subjectAssignedId")
    private Integer subjectAssignedId;

    @JsonProperty("subjectCode")
    private String subjectCode;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("teacherName")
    private String teacherName;

    @JsonProperty("schedule")
    private String schedule;

    @JsonProperty("classroom")
    private String classroom;

    @JsonProperty("section")
    private String section;

    @JsonProperty("periodName")
    private String periodName;

    @JsonProperty("availableSpace")
    private Integer availableSpace;

    @JsonProperty("maximumCapacity")
    private Integer maximumCapacity;

    // Lista de inscripciones
    @JsonProperty("inscriptions")
    private List<InscriptionSummaryDTO> inscriptions;
}
