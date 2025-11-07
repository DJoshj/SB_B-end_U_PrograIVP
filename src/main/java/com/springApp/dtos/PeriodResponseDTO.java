package com.springApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodResponseDTO {
    private Long periodId;
    private String name;
    private Integer year;
    private LocalDate startDate;
    private LocalDate endDate;
    private String schedule;
}
