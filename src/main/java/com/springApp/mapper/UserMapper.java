package com.springApp.mapper;

import com.springApp.dtos.UserDTO;
import com.springApp.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    //convierte un dto a un objeto
    public UserEntity toEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, UserEntity.class);
    }

    //actualiza un usuario existente con valores de un dto
    public void toEntity(UserDTO userDTO, UserEntity existingUser){
        modelMapper.map(userDTO, existingUser);
    }

    //entidad a dto
    public UserDTO toDTO(UserEntity user){
        return modelMapper.map(user,UserDTO.class);
    }
}
