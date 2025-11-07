package com.springApp.mapper;

import com.springApp.dtos.TeacherResponseDTO;
import com.springApp.entity.TeacherEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    @Autowired
    private ModelMapper modelMapper;

    //dto a entity
    public TeacherEntity toEntity(TeacherResponseDTO teacherResponseDTO){
        return modelMapper.map(teacherResponseDTO, TeacherEntity.class);
    }

    //entidad a dto
    public TeacherResponseDTO toDTO(TeacherEntity teacher) {
        return modelMapper.map(teacher, TeacherResponseDTO.class);
    }
}
