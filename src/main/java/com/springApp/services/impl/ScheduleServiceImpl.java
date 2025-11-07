package com.springApp.services.impl;

import com.springApp.dtos.ScheduleDTO;
import com.springApp.dtos.ScheduleResponseDTO;
import com.springApp.entity.ScheduleEntity;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.ScheduleMapper;
import com.springApp.repositories.ScheduleRepository;
import com.springApp.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleMapper scheduleMapper;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public ScheduleResponseDTO createSchedule(ScheduleDTO dto) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setDays(dto.getDays());
        schedule.setSchedule(dto.getSchedule());

        return scheduleMapper.toDTO(scheduleRepository.save(schedule));
    }

    @Override
    @Transactional
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleDTO dto) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));

        schedule.setDays(dto.getDays());
        schedule.setSchedule(dto.getSchedule());

        return scheduleMapper.toDTO(scheduleRepository.save(schedule));
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDTO getScheduleById(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));
        return scheduleMapper.toDTO(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDTO> getSchedulesByDay(String day) {
        return scheduleRepository.findByDaysContaining(day).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllDistinctDays() {
        return scheduleRepository.findAllDistinctDays();
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Horario no encontrado");
        }
        scheduleRepository.deleteById(id);
    }
}
