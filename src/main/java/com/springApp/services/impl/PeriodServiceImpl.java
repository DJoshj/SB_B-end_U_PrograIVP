package com.springApp.services.impl;

import com.springApp.dtos.PeriodDTO;
import com.springApp.dtos.PeriodResponseDTO;
import com.springApp.entity.PeriodEntity;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.PeriodMapper;
import com.springApp.repositories.PeriodRepository;
import com.springApp.services.PeriodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {
    private final PeriodMapper periodMapper;
    private final PeriodRepository periodRepository;

    @Override
    @Transactional
    public PeriodResponseDTO createPeriod(PeriodDTO dto) {
        if (periodRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Ya existe un periodo con ese nombre");
        }

        PeriodEntity period = new PeriodEntity();
        period.setName(dto.getName());
        period.setYear(dto.getYear());
        period.setStartDate(dto.getStartDate());
        period.setEndDate(dto.getEndDate());
        period.setSchedule(dto.getSchedule());

        return periodMapper.toDTO(periodRepository.save(period));
    }

    @Override
    @Transactional
    public PeriodResponseDTO updatePeriod(Long id, PeriodDTO dto) {
        PeriodEntity period = periodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Periodo no encontrado"));

        period.setName(dto.getName());
        period.setYear(dto.getYear());
        period.setStartDate(dto.getStartDate());
        period.setEndDate(dto.getEndDate());
        period.setSchedule(dto.getSchedule());

        return periodMapper.toDTO(periodRepository.save(period));
    }

    @Override
    @Transactional(readOnly = true)
    public PeriodResponseDTO getPeriodById(Long id) {
        PeriodEntity period = periodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Periodo no encontrado"));
        return periodMapper.toDTO(period);
    }

    @Override
    @Transactional(readOnly = true)
    public PeriodResponseDTO getCurrentPeriod() {
        PeriodEntity period = periodRepository.findCurrentPeriod()
                .orElseThrow(() -> new ResourceNotFoundException("No hay periodo activo actualmente"));
        return periodMapper.toDTO(period);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeriodResponseDTO> getAllPeriods() {
        return periodRepository.findAllOrderedByYearAndDate().stream()
                .map(periodMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeriodResponseDTO> getPeriodsByYear(Integer year) {
        return periodRepository.findByYear(year).stream()
                .map(periodMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAllYears() {
        return periodRepository.findAllYears();
    }
    @Override
    @Transactional
    public void deletePeriod(Long id) {
        if (!periodRepository.existsById(id)) {
            throw new ResourceNotFoundException("Periodo no encontrado");
        }
        periodRepository.deleteById(id);
    }
}
