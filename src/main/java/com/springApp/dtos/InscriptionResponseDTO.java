package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de respuesta para inscripción
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionResponseDTO {
    @JsonProperty("inscriptionId")
    private Long inscriptionId;

    // Información del estudiante
    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("studentCarnet")
    private String studentCarnet;

    @JsonProperty("studentEmail")
    private String studentEmail;

    // Información de la materia asignada
    @JsonProperty("subjectAssignedId")
    private Long subjectAssignedId;

    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("subjectCode")
    private String subjectCode;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("valueUnits")
    private Integer valueUnits;

    // Información del docente
    @JsonProperty("teacherId")
    private Long teacherId;

    @JsonProperty("teacherName")
    private String teacherName;

    @JsonProperty("teacherCode")
    private String teacherCode;

    // Información de horario y aula
    @JsonProperty("scheduleId")
    private Long scheduleId;

    @JsonProperty("scheduleDays")
    private String scheduleDays;

    @JsonProperty("scheduleTime")
    private String scheduleTime;

    @JsonProperty("classroomId")
    private Long classroomId;

    @JsonProperty("classroomName")
    private String classroomName;

    @JsonProperty("building")
    private String building;

    @JsonProperty("section")
    private String section;

    // Información del periodo
    @JsonProperty("periodId")
    private Long periodId;

    @JsonProperty("periodName")
    private String periodName;

    @JsonProperty("periodYear")
    private Integer periodYear;

    // Estado e información de inscripción
    @JsonProperty("state")
    private String state;

    @JsonProperty("inscriptionDate")
    private String inscriptionDate;

    // Información de cupos
    @JsonProperty("availableSpace")
    private Integer availableSpace;

    @JsonProperty("maximumCapacity")
    private Integer maximumCapacity;
}
