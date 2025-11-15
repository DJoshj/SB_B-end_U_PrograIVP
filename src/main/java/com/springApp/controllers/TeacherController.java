package com.springApp.controllers;

import com.springApp.dtos.StudentResponseDTO;
import com.springApp.dtos.TeacherDTO;
import com.springApp.dtos.TeacherResponseDTO;
import com.springApp.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create")
    public ResponseEntity<?> createTeacher(@Valid @RequestBody TeacherDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createTeacher(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherDTO dto) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, dto));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id ) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping("/code/{teacherCode}")
    public ResponseEntity<?> getTeacherByCode(@PathVariable String teacherCode) {
        return ResponseEntity.ok(teacherService.getTeacherByCode(teacherCode));
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<?> getTeachersBySpecialty(@PathVariable String specialty) {
        List<TeacherResponseDTO> teacherSpeciality = teacherService.getTeachersBySpecialty(specialty);

        if (teacherSpeciality.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron profesores con la especialidad: " + specialty);
        }

        return ResponseEntity.ok(teacherSpeciality);
    }


    @GetMapping("/all")
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/specialties")
    public ResponseEntity<?> getAllSpecialties() {
        return ResponseEntity.ok(teacherService.getAllSpecialties());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
