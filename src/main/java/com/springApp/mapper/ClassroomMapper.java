package com.springApp.mapper;

import com.springApp.dtos.ClassroomResponseDTO;
import com.springApp.entity.ClassroomEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassroomMapper {
    @Autowired
    private ModelMapper modelMapper;

    //dto a entity
    public ClassroomEntity toEntity(ClassroomResponseDTO responseDTO){
        return modelMapper.map(responseDTO, ClassroomEntity.class);
    }

    //entidad a dto
    public ClassroomResponseDTO toDTO(ClassroomEntity classroom) {
        return modelMapper.map(classroom, ClassroomResponseDTO.class);
    }
}
