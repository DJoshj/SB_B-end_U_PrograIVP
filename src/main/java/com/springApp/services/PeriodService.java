package com.springApp.services;

import com.springApp.dtos.PeriodDTO;
import com.springApp.dtos.PeriodResponseDTO;

import java.util.List;

public interface PeriodService {
    PeriodResponseDTO createPeriod(PeriodDTO dto);
    PeriodResponseDTO updatePeriod(Long id, PeriodDTO dto);
    PeriodResponseDTO getPeriodById(Long id);
    PeriodResponseDTO getCurrentPeriod();
    List<PeriodResponseDTO> getAllPeriods();
    List<PeriodResponseDTO> getPeriodsByYear(Integer year);
    List<Integer> getAllYears();
    void deletePeriod(Long id);
}
