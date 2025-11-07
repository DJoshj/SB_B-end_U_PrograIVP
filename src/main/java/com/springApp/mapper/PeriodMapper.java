package com.springApp.mapper;

import com.springApp.dtos.PeriodResponseDTO;
import com.springApp.entity.PeriodEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PeriodMapper {
    @Autowired
    private ModelMapper modelMapper;

    //dto a entity
    public PeriodEntity toEntity(PeriodResponseDTO responseDTO){
        return modelMapper.map(responseDTO, PeriodEntity.class);
    }

    //entidad a dto
    public PeriodResponseDTO toDTO(PeriodEntity period) {
        return modelMapper.map(period, PeriodResponseDTO.class);
    }
}
