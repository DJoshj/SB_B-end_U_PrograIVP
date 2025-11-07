package com.springApp.controllers;

import com.springApp.dtos.ClassroomDTO;
import com.springApp.dtos.ClassroomResponseDTO;
import com.springApp.services.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/create")
    public ResponseEntity<ClassroomResponseDTO> createClassroom(@Valid @RequestBody ClassroomDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(classroomService.createClassroom(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClassroomResponseDTO> updateClassroom(
            @PathVariable Long id,
            @Valid @RequestBody ClassroomDTO dto) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponseDTO> getClassroomById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClassroomResponseDTO>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @GetMapping("/building/{building}")
    public ResponseEntity<List<ClassroomResponseDTO>> getClassroomsByBuilding(@PathVariable String building) {
        return ResponseEntity.ok(classroomService.getClassroomsByBuilding(building));
    }

    @GetMapping("/capacity/{minCapacity}")
    public ResponseEntity<List<ClassroomResponseDTO>> getClassroomsByMinCapacity(@PathVariable Integer minCapacity) {
        return ResponseEntity.ok(classroomService.getClassroomsByMinCapacity(minCapacity));
    }

    @GetMapping("/buildings")
    public ResponseEntity<List<String>> getAllBuildings() {
        return ResponseEntity.ok(classroomService.getAllBuildings());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }
}
