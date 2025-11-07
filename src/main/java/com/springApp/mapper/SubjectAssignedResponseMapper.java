package com.springApp.mapper;

import com.springApp.dtos.SubjectAssignedResponseDTO;
import com.springApp.entity.SubjectAssignedEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectAssignedResponseMapper {
    @Autowired
    private ModelMapper modelMapper;

    //convierte un dto a un objeto
    public SubjectAssignedEntity toEntity(SubjectAssignedResponseDTO subjectAssignedDTO){
        return modelMapper.map(subjectAssignedDTO, SubjectAssignedEntity.class);
    }

    //entidad a dto
    public SubjectAssignedResponseDTO toDTO(SubjectAssignedEntity subjectAssigned) {
        return modelMapper.map(subjectAssigned, SubjectAssignedResponseDTO.class);
    }
}
