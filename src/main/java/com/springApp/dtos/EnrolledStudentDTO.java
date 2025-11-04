package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrolledStudentDTO {
    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("carnet")
    private String carnet;

    @JsonProperty("email")
    private String email;

    @JsonProperty("inscriptionDate")
    private String inscriptionDate;

    @JsonProperty("state")
    private String state;
}
