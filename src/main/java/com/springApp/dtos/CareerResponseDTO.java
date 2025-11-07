package com.springApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareerResponseDTO {
    private Long careerId;
    private String nameCareer;
    private String faculty;
}
