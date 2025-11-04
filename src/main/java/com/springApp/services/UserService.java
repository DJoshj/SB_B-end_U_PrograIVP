package com.springApp.services;

import com.springApp.dtos.UserDTO;
import com.springApp.entity.states.UserState;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createuser(Long rolID, UserDTO userDTO);
    List<UserDTO> listUsers();
    Optional<UserDTO> searchByUsername(String username);
    Optional<UserDTO> searchById(Long userId);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    UserDTO changeUserState(Long userId, UserState newUserState);
    List<UserDTO> getUserByState(UserState userState);
    UserDTO updatePassword(String username, UserDTO userDTO);
}
