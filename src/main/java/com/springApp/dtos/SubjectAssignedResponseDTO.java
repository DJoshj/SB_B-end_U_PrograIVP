package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de respuesta para materia asignada
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssignedResponseDTO {
    @JsonProperty("subjectAssignedId")
    private Integer subjectAssignedId;

    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("subjectCode")
    private String subjectCode;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("teacherId")
    private Long teacherId;

    @JsonProperty("teacherName")
    private String teacherName;

    @JsonProperty("teacherCode")
    private String teacherCode;

    @JsonProperty("periodId")
    private Long periodId;

    @JsonProperty("periodName")
    private String periodName;

    @JsonProperty("scheduleId")
    private Integer scheduleId;

    @JsonProperty("scheduleDays")
    private String scheduleDays;

    @JsonProperty("scheduleTime")
    private String scheduleTime;

    @JsonProperty("classroomId")
    private Integer classroomId;

    @JsonProperty("classroomName")
    private String classroomName;

    @JsonProperty("building")
    private String building;

    @JsonProperty("section")
    private String section;

    @JsonProperty("availableSpace")
    private Integer availableSpace;

    @JsonProperty("maximumCapacity")
    private Integer maximumCapacity;

    @JsonProperty("enrolledStudents")
    private Integer enrolledStudents;
}
