package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO resumido para inscripciones dentro de SubjectAssigned
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionSummaryDTO {
    @JsonProperty("inscriptionId")
    private Long inscriptionId;

    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("studentCarnet")
    private String studentCarnet;

    @JsonProperty("studentEmail")
    private String studentEmail;

    @JsonProperty("inscriptionDate")
    private String inscriptionDate;

    @JsonProperty("state")
    private String state;
}
