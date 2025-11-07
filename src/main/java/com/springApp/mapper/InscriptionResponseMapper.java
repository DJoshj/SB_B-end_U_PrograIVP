package com.springApp.mapper;

import com.springApp.dtos.InscriptionResponseDTO;
import com.springApp.dtos.SubjectAssignedWithInscriptionsDTO;
import com.springApp.entity.InscriptionsEntity;
import com.springApp.entity.SubjectAssignedEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InscriptionResponseMapper {
    @Autowired
    private ModelMapper modelMapper;

    //convierte un dto a un objeto
    public InscriptionsEntity toEntity(InscriptionResponseDTO inscriptionResponseDTO){
        return modelMapper.map(inscriptionResponseDTO, InscriptionsEntity.class);
    }

    //entidad a dto
    public InscriptionResponseDTO toDTO(InscriptionsEntity inscriptionsEntity) {
        return modelMapper.map(inscriptionsEntity, InscriptionResponseDTO.class);
    }
}
