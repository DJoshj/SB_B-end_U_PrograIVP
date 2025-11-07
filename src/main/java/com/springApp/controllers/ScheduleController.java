package com.springApp.controllers;

import com.springApp.dtos.ScheduleDTO;
import com.springApp.dtos.ScheduleResponseDTO;
import com.springApp.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@Valid @RequestBody ScheduleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDTO dto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByDay(@PathVariable String day) {
        return ResponseEntity.ok(scheduleService.getSchedulesByDay(day));
    }

    @GetMapping("/days")
    public ResponseEntity<List<String>> getAllDistinctDays() {
        return ResponseEntity.ok(scheduleService.getAllDistinctDays());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long  id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
