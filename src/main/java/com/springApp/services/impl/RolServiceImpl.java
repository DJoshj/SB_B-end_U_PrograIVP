package com.springApp.services.impl;

import com.springApp.dtos.RolDTO;
import com.springApp.entity.RolEntity;
import com.springApp.exception.BadRequestException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.RolMapper;
import com.springApp.repositories.RolRepository;
import com.springApp.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private RolMapper rolMapper;

    @Override
    public RolDTO createRol(RolDTO rol) {
        if(rolRepository.existsByName(rol.getName())){
            throw new BadRequestException("Already exist an rol with this name!");
        }

        RolEntity rolEntity = rolMapper.toEntity(rol);
        RolEntity newRol = rolRepository.save(rolEntity);

        return rolMapper.toDTO(newRol);
    }

    @Override
    public List<RolDTO> listRoles() {
        List<RolEntity> roles = rolRepository.findAll();
        return roles.stream()
                .map(rolMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RolDTO> getRolById(Long idRol) {
        Optional<RolEntity> rol = rolRepository.findById(idRol);
        return rol.map(rolMapper::toDTO);
    }

    @Override
    public RolDTO updateRol(Long idRol, RolDTO rolDTO) {
        RolEntity existingRol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrada"));

        existingRol.setName(rolDTO.getName());

        RolEntity rolUpdated = rolRepository.save(existingRol);
        return rolMapper.toDTO(rolUpdated);
    }

    @Override
    public void deleteRol(Long idRol) {
        Optional<RolEntity> existingRol = rolRepository.findById(idRol);

        if(!existingRol.isPresent()){
            throw new ResourceNotFoundException("Rol not found to delete.");
        }
        rolRepository.deleteById(idRol);
    }
}
