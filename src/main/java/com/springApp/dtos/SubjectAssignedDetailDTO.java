package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// ==========================================
// 3. DTOs PARA LISTADOS Y CONSULTAS
// ==========================================
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssignedDetailDTO {
    @JsonProperty("subjectAssigned")
    private SubjectAssignedResponseDTO subjectAssigned;

    @JsonProperty("totalEnrolled")
    private Integer totalEnrolled;

    @JsonProperty("enrolledStudents")
    private List<EnrolledStudentDTO> enrolledStudents;
}
