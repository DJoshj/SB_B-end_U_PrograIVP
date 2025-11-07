package com.springApp.services;

import com.springApp.dtos.ClassroomDTO;
import com.springApp.dtos.ClassroomResponseDTO;

import java.util.List;

public interface ClassroomService {
    ClassroomResponseDTO createClassroom(ClassroomDTO dto);
    ClassroomResponseDTO updateClassroom(Long id, ClassroomDTO dto);
    ClassroomResponseDTO getClassroomById(Long id);
    List<ClassroomResponseDTO> getAllClassrooms();
    List<ClassroomResponseDTO> getClassroomsByBuilding(String building);
    List<ClassroomResponseDTO> getClassroomsByMinCapacity(Integer minCapacity);
    List<String> getAllBuildings();
    void deleteClassroom(Long id);
}
