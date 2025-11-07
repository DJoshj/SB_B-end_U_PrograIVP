package com.springApp.services;

import com.springApp.dtos.TeacherDTO;
import com.springApp.dtos.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {
    TeacherResponseDTO createTeacher(TeacherDTO dto);
    TeacherResponseDTO updateTeacher(Long id, TeacherDTO dto);
    TeacherResponseDTO getTeacherById(Long id);
    TeacherResponseDTO getTeacherByCode(String teacherCode);
    List<TeacherResponseDTO> getAllTeachers();
    List<TeacherResponseDTO> getTeachersBySpecialty(String specialty);
    List<TeacherResponseDTO> searchTeachers(String search);
    List<String> getAllSpecialties();
    void deleteTeacher(Long id);
}
