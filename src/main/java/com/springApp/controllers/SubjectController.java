package com.springApp.controllers;

import com.springApp.dtos.SubjectDTO;
import com.springApp.dtos.SubjectResponseDTO;
import com.springApp.services.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private  SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<SubjectResponseDTO> createSubject(@Valid @RequestBody SubjectDTO dto ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.createSubject(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(
            @PathVariable Long id,
            @Valid @RequestBody SubjectDTO dto) {
        return ResponseEntity.ok(subjectService.updateSubject(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping("/code/{subjectCode}")
    public ResponseEntity<SubjectResponseDTO> getSubjectByCode(@PathVariable String subjectCode) {
        return ResponseEntity.ok(subjectService.getSubjectByCode(subjectCode));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
    @GetMapping("/career/{careerId}")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjectsByCareer(@PathVariable Long careerId) {
        return ResponseEntity.ok(subjectService.getSubjectsByCareer(careerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
