package com.springApp.mapper;

import com.springApp.dtos.StudentResponseDTO;
import com.springApp.entity.StudentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    @Autowired
    private  ModelMapper modelMapper;

    //dto a entity
    public StudentEntity toEntity(StudentResponseDTO studentResponseDTO){
        return modelMapper.map(studentResponseDTO, StudentEntity.class);
    }

    //entidad a dto
    /*public StudentResponseDTO toDTO(StudentEntity student) {
        return modelMapper.map(student, StudentResponseDTO.class);
    }*/
    public StudentResponseDTO toDTO(StudentEntity student) {
        StudentResponseDTO dto = modelMapper.map(student, StudentResponseDTO.class);

        // Mapeo manual de los datos del usuario
        if (student.getUser() != null) {
            dto.setUserId(student.getUser().getUserId());
            dto.setUsername(student.getUser().getUsername());
        }

        // Mapeo de la carrera (si la tienes)
        if (student.getCareer() != null) {
            dto.setCareerId(student.getCareer().getCareerId());
            dto.setCareerName(student.getCareer().getNameCareer());
        }

        return dto;
    }
}
