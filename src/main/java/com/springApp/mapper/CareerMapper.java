package com.springApp.mapper;

import com.springApp.dtos.CareerResponseDTO;
import com.springApp.entity.CareerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CareerMapper {
    @Autowired
    private ModelMapper modelMapper;

    //dto a entity
    public CareerEntity toEntity(CareerResponseDTO careerResponseDTO){
        return modelMapper.map(careerResponseDTO, CareerEntity.class);
    }

    //entidad a dto
    public CareerResponseDTO toDTO(CareerEntity career) {
        return modelMapper.map(career, CareerResponseDTO.class);
    }
}
