package com.springApp.controllers;

import com.springApp.dtos.SubjectAssignedDTO;
import com.springApp.dtos.SubjectAssignedResponseDTO;
import com.springApp.dtos.SubjectAssignedWithInscriptionsDTO;
import com.springApp.dtos.SubjectResponseDTO;
import com.springApp.services.SubjectAssignedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject-assignments")
@RequiredArgsConstructor
public class SubjectAssignedController {
    //inyección de dependencia
    private final SubjectAssignedService subjectAssignedService;

    /**
     * Asignar una materia a un docente
     */
    @PostMapping("/create")
    public ResponseEntity<?> assignSubject(@Valid @RequestBody SubjectAssignedDTO dto) {
        try {
            SubjectAssignedResponseDTO response = subjectAssignedService.assignSubject(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Actualizar una materia asignada existente
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSubjectAssignment(
            @PathVariable Long id,
            @Valid @RequestBody SubjectAssignedDTO dto) {
        try {
            SubjectAssignedResponseDTO response = subjectAssignedService.updateSubjectAssignment(id, dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectAssignedResponseDTO>> getAllSubjectAssignments() {
        return ResponseEntity.ok(subjectAssignedService.getAllSubjectAssignments());
    }

    /**
            * Obtener materias por periodo
     */
    @GetMapping("/period/{periodId}")
    public ResponseEntity<List<SubjectAssignedResponseDTO>> getByPeriod(
            @PathVariable Long periodId) {
        List<SubjectAssignedResponseDTO> subjects =
                subjectAssignedService.getSubjectsByPeriod(periodId);
        return ResponseEntity.ok(subjects);
    }

    /**
     * Obtener materias disponibles (con cupo)
     */
    @GetMapping("/available/{periodId}")
    public ResponseEntity<List<SubjectAssignedResponseDTO>> getAvailable(
            @PathVariable Long periodId) {
        List<SubjectAssignedResponseDTO> subjects =
                subjectAssignedService.getAvailableSubjects(periodId);
        return ResponseEntity.ok(subjects);
    }

    /**
     * Obtener materia por ID (básico, sin inscripciones)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            SubjectAssignedResponseDTO subject =
                    subjectAssignedService.getSubjectAssignedById(id);
            return ResponseEntity.ok(subject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtener materia por ID con inscripciones
     */
    @GetMapping("/{id}/inscriptions")
    public ResponseEntity<?> getByIdWithInscriptions(@PathVariable Long id) {
        try {
            SubjectAssignedWithInscriptionsDTO subject =
                    subjectAssignedService.getSubjectAssignedWithInscriptions(id);
            return ResponseEntity.ok(subject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtener materias por docente
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<SubjectAssignedResponseDTO>> getByTeacher(
            @PathVariable Long teacherId) {
        List<SubjectAssignedResponseDTO> subjects =
                subjectAssignedService.getSubjectsByTeacher(teacherId);
        return ResponseEntity.ok(subjects);
    }

    /**
     * Obtener materias por aula
     */
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<SubjectAssignedResponseDTO>> getByClassroom(
            @PathVariable Long classroomId) {
        List<SubjectAssignedResponseDTO> subjects =
                subjectAssignedService.getSubjectsByClassroom(classroomId);
        return ResponseEntity.ok(subjects);
    }

}
