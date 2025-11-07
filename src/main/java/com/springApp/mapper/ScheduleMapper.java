package com.springApp.mapper;

import com.springApp.dtos.ScheduleResponseDTO;
import com.springApp.dtos.StudentResponseDTO;
import com.springApp.entity.ScheduleEntity;
import com.springApp.entity.StudentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {
    @Autowired
    private ModelMapper modelMapper;

    //dto a entity
    public ScheduleEntity toEntity(ScheduleResponseDTO responseDTO){
        return modelMapper.map(responseDTO, ScheduleEntity.class);
    }

    //entidad a dto
    public ScheduleResponseDTO toDTO(ScheduleEntity schedule) {
        return modelMapper.map(schedule, ScheduleResponseDTO.class);
    }
}
