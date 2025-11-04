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
public class StudentInscriptionsDTO {
    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("studentCarnet")
    private String studentCarnet;

    @JsonProperty("totalInscriptions")
    private Integer totalInscriptions;

    @JsonProperty("activeInscriptions")
    private Integer activeInscriptions;

    @JsonProperty("completedInscriptions")
    private Integer completedInscriptions;

    @JsonProperty("inscriptions")
    private List<InscriptionResponseDTO> inscriptions;
}
