package com.springApp.mapper;

import com.springApp.dtos.SubjectResponseDTO;
import com.springApp.entity.SubjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
    @Autowired
    private ModelMapper modelMapper;

    //dto a entity
    public SubjectEntity toEntity(SubjectResponseDTO responseDTO){
        return modelMapper.map(responseDTO, SubjectEntity.class);
    }

    //entidad a dto
    public SubjectResponseDTO toDTO(SubjectEntity subject) {
        return modelMapper.map(subject, SubjectResponseDTO.class);
    }
}
