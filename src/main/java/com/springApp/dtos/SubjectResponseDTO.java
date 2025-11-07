package com.springApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponseDTO {
    private Long subjectId;
    private String subjectCode;
    private String name;
    private Integer valueUnits;
    private Long careerId;
    private String careerName;
}
