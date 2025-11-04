package com.springApp.services.impl;

import com.springApp.dtos.UserDTO;
import com.springApp.entity.RolEntity;
import com.springApp.entity.UserEntity;
import com.springApp.entity.states.UserState;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.UserMapper;
import com.springApp.repositories.RolRepository;
import com.springApp.repositories.UserRepository;
import com.springApp.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    //injection of dependency
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDTO createuser(Long rolID, UserDTO userDTO) {
        UserEntity user = userMapper.toEntity(userDTO);

        RolEntity rol = rolRepository.findById(rolID)
                .orElseThrow(()-> new ResourceNotFoundException("Rol with ID "+rolID+ " not found"));

        // Validar que el usuario no exista
        if (userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("The User already exists!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("The email already is registered!");
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //estado inicial (si no se envía en el DTO)
        if (user.getState() == null) {
            user.setState(UserState.ACTIVE);
        }
        // Asignar rol
        user.setRoles(rol);


        UserEntity userSaved = userRepository.save(user);

        return userMapper.toDTO(userSaved);
    }

    @Override
    public List<UserDTO> listUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<UserDTO> searchByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> searchById(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        return user.map(userMapper::toDTO);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId+ " not found"));

        existingUser.setUsername(userDTO.getUsername());

        //encriptar la contraseña solo si la contraseña nueva no está vacía:
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setState(userDTO.getState());

        if(userDTO.getRoles() != null && userDTO.getRoles().getRolId() != null){
            RolEntity rol = rolRepository.findById(userDTO.getRoles().getRolId())
                    .orElseThrow(()-> new ResourceNotFoundException("Usuario con ID no encontrada"));

            existingUser.setRoles(rol);
        }


        UserEntity userUpdated = userRepository.save(existingUser);

        return userMapper.toDTO(userUpdated);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID "+ userId+ " not found"));
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO changeUserState(Long userId, UserState newUserState) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId+ " not found"));

        existingUser.setState(newUserState);

        UserEntity userUpdated = userRepository.save(existingUser);

        return userMapper.toDTO(userUpdated);
    }

    @Override
    public List<UserDTO> getUserByState(UserState userState) {
        List<UserEntity> user = userRepository.findByState(userState);
        return user.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO updatePassword(String username, UserDTO userDTO) {
        // Buscar el usuario por su username
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));

        // Actualizar la contraseña
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Guardar los cambios
        UserEntity passwordUpdated = userRepository.save(user);
        return userMapper.toDTO(passwordUpdated);
    }
}
