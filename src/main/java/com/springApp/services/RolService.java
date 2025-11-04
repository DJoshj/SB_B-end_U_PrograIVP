package com.springApp.services;

import com.springApp.dtos.RolDTO;

import java.util.List;
import java.util.Optional;

public interface RolService {
    RolDTO createRol(RolDTO  rol);
    List<RolDTO> listRoles();
    Optional<RolDTO> getRolById(Long idRol);
    RolDTO updateRol(Long idRol, RolDTO rolDTO);
    void deleteRol(Long idRol);
}
