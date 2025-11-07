package com.springApp.mapper;

import com.springApp.dtos.StudentResponseDTO;
import com.springApp.entity.StudentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    @Autowired
    private ModelMapper modelMapper;

    //dto a entity
    public StudentEntity toEntity(StudentResponseDTO studentResponseDTO){
        return modelMapper.map(studentResponseDTO, StudentEntity.class);
    }

    //entidad a dto
    public StudentResponseDTO toDTO(StudentEntity student) {
        return modelMapper.map(student, StudentResponseDTO.class);
    }
}
