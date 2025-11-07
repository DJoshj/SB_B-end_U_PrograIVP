package com.springApp.mapper;

import com.springApp.dtos.SubjectAssignedWithInscriptionsDTO;
import com.springApp.entity.SubjectAssignedEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectAssignedWithInscriptionsMapper {
    @Autowired
    private ModelMapper modelMapper;

    //convierte un dto a un objeto
    public SubjectAssignedEntity toEntity(SubjectAssignedWithInscriptionsDTO subjectAssignedWithInscriptionsDTO){
        return modelMapper.map(subjectAssignedWithInscriptionsDTO, SubjectAssignedEntity.class);
    }

    //entidad a dto
    public SubjectAssignedWithInscriptionsDTO toDTO(SubjectAssignedEntity subjectAssigned) {
        return modelMapper.map(subjectAssigned, SubjectAssignedWithInscriptionsDTO.class);
    }
}
