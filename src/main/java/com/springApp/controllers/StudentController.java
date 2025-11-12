package com.springApp.controllers;

import com.springApp.dtos.StudentDTO;
import com.springApp.dtos.StudentResponseDTO;
import com.springApp.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO dto) {
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/carnet/{carnet}")
    public ResponseEntity<StudentResponseDTO> getStudentByCarnet(@PathVariable String carnet) {
        return ResponseEntity.ok(studentService.getStudentByCarnet(carnet));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/career/{careerId}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByCareer(@PathVariable Long careerId) {
        return ResponseEntity.ok(studentService.getStudentsByCareer(careerId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/career/{careerId}/count")
    public ResponseEntity<Long> countStudentsByCareer(@PathVariable Long careerId) {
        return ResponseEntity.ok(studentService.countStudentsByCareer(careerId));
    }


}
