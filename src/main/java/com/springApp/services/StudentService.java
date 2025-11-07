package com.springApp.services;

import com.springApp.dtos.StudentDTO;
import com.springApp.dtos.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    StudentResponseDTO createStudent(StudentDTO dto);
    StudentResponseDTO updateStudent(Long id, StudentDTO dto);
    StudentResponseDTO getStudentById(Long id);
    StudentResponseDTO getStudentByCarnet(String carnet);
    List<StudentResponseDTO> getAllStudents();
    List<StudentResponseDTO> getStudentsByCareer(Long careerId);
    List<StudentResponseDTO> searchStudents(String search);
    void deleteStudent(Long id);
    Long countStudentsByCareer(Long careerId);
}
