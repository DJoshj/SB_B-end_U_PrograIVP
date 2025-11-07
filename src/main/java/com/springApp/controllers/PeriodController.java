package com.springApp.controllers;

import com.springApp.dtos.PeriodDTO;
import com.springApp.dtos.PeriodResponseDTO;
import com.springApp.services.PeriodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/periods")
public class PeriodController {
    @Autowired
    private PeriodService periodService;

    @PostMapping("/create")
    public ResponseEntity<PeriodResponseDTO> createPeriod(@Valid @RequestBody PeriodDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(periodService.createPeriod(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PeriodResponseDTO> updatePeriod(
            @PathVariable Long id,
            @Valid @RequestBody PeriodDTO dto) {
        return ResponseEntity.ok(periodService.updatePeriod(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodResponseDTO> getPeriodById(@PathVariable Long id) {
        return ResponseEntity.ok(periodService.getPeriodById(id));
    }

    @GetMapping("/current")
    public ResponseEntity<PeriodResponseDTO> getCurrentPeriod() {
        return ResponseEntity.ok(periodService.getCurrentPeriod());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PeriodResponseDTO>> getAllPeriods() {
        return ResponseEntity.ok(periodService.getAllPeriods());
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<PeriodResponseDTO>> getPeriodsByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(periodService.getPeriodsByYear(year));
    }

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getAllYears() {
        return ResponseEntity.ok(periodService.getAllYears());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePeriod(@PathVariable Long id) {
        periodService.deletePeriod(id);
        return ResponseEntity.noContent().build();
    }
}
