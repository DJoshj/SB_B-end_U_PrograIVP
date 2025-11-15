package com.springApp.controllers;

import com.springApp.dtos.CareerDTO;
import com.springApp.dtos.CareerResponseDTO;
import com.springApp.services.CareerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/career")
@RequiredArgsConstructor
public class CareerController {
    private final CareerService careerService;

    @PostMapping("/create")
    public ResponseEntity<CareerResponseDTO> createCareer (@Valid @RequestBody CareerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(careerService.createCareer(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CareerResponseDTO> updateCareer(
            @PathVariable Long id,
            @Valid @RequestBody CareerDTO dto) {
        return ResponseEntity.ok(careerService.updateCareer(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerResponseDTO> getCareerById(@PathVariable Long id) {
        return ResponseEntity.ok(careerService.getCareerById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CareerResponseDTO>> getAllCareers() {
        return ResponseEntity.ok(careerService.getAllCareers());
    }

    @GetMapping("/faculty/{faculty}")
    public ResponseEntity<List<CareerResponseDTO>> getCareersByFaculty(@PathVariable String faculty) {
        return ResponseEntity.ok(careerService.getCareersByFaculty(faculty));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CareerResponseDTO>> searchCareers(@RequestParam String query) {
        return ResponseEntity.ok(careerService.searchCareers(query));
    }

    @GetMapping("/allFaculties")
    public ResponseEntity<List<String>> getAllFaculties() {
        return ResponseEntity.ok(careerService.getAllFaculties());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return ResponseEntity.noContent().build();
    }
}
