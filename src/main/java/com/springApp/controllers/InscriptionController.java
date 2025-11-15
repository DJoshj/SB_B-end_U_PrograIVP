package com.springApp.controllers;

import com.springApp.dtos.InscriptionRequestDTO;
import com.springApp.dtos.InscriptionResponseDTO;
import com.springApp.services.InscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de inscripciones
 */
@RestController
@RequestMapping("/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {
    private final InscriptionService inscriptionService;
    /**
     * Inscribir un estudiante en varias materias materia. Ej.
     * {
     *   "studentId": 2,
     *   "periodId": 1,
     *   "subjectsAssigned": [3, 4, 7]
     * }
     *
     */
    @PostMapping
    public ResponseEntity<?> enrollStudent(@Valid @RequestBody  InscriptionRequestDTO dto) {
        try {
            List<InscriptionResponseDTO> responses = inscriptionService.enrollStudent(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Cancelar una inscripción
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelInscription(@PathVariable Long id) {
        try {
            inscriptionService.cancelInscription(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inscripción cancelada exitosamente");
            response.put("inscriptionId", id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtener todas las inscripciones de un estudiante
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<InscriptionResponseDTO>> getStudentInscriptions(
            @PathVariable Long studentId) {
        List<InscriptionResponseDTO> inscriptions =
                inscriptionService.getStudentInscriptions(studentId);
        return ResponseEntity.ok(inscriptions);
    }

    /**
     * Obtener inscripciones de un estudiante por periodo
     */
    @GetMapping("/student/{studentId}/period/{periodId}")
    public ResponseEntity<List<InscriptionResponseDTO>> getByStudentAndPeriod(
            @PathVariable Long studentId,
            @PathVariable Long periodId) {
        List<InscriptionResponseDTO> inscriptions =
                inscriptionService.getStudentInscriptionsByPeriod(studentId, periodId);
        return ResponseEntity.ok(inscriptions);
    }

    /**
     * Obtener una inscripción por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getInscriptionById(@PathVariable Long id) {
        try {
            InscriptionResponseDTO inscription = inscriptionService.getInscriptionById(id);
            return ResponseEntity.ok(inscription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Verificar si un estudiante puede inscribirse en una materia
     */
    @GetMapping("/can-enroll")
    public ResponseEntity<Map<String, Object>> canEnroll(
            @RequestParam Long studentId,
            @RequestParam Long subjectAssignedId) {
        boolean canEnroll = inscriptionService.canEnroll(studentId, subjectAssignedId);
        Map<String, Object> response = new HashMap<>();
        response.put("canEnroll", canEnroll);
        response.put("studentId", studentId);
        response.put("subjectAssignedId", subjectAssignedId);
        return ResponseEntity.ok(response);
    }

    /**
     * Contar inscripciones activas de un estudiante en un periodo
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countActiveInscriptions(
            @RequestParam Long studentId,
            @RequestParam Long periodId) {
        Long count = inscriptionService.countActiveInscriptionsByStudentAndPeriod(studentId, periodId);
        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("periodId", periodId);
        response.put("activeInscriptions", count);
        return ResponseEntity.ok(response);
    }


}
