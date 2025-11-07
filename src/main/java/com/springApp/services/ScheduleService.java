package com.springApp.services;

import com.springApp.dtos.ScheduleDTO;
import com.springApp.dtos.ScheduleResponseDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDTO createSchedule(ScheduleDTO dto);
    ScheduleResponseDTO updateSchedule(Long id, ScheduleDTO dto);
    ScheduleResponseDTO getScheduleById(Long id);
    List<ScheduleResponseDTO> getAllSchedules();
    List<ScheduleResponseDTO> getSchedulesByDay(String day);
    List<String> getAllDistinctDays();
    void deleteSchedule(Long id);
}
