package com.springApp.mapper;

import com.springApp.dtos.RolDTO;
import com.springApp.entity.RolEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {
    @Autowired
    private ModelMapper modelMapper;

    public RolDTO toDTO(RolEntity rol){
        return modelMapper.map(rol, RolDTO.class);
    }

    public RolEntity toEntity(RolDTO rolDTO){
        return modelMapper.map(rolDTO, RolEntity.class);
    }
}
