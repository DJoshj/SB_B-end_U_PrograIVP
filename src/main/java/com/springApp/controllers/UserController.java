package com.springApp.controllers;

import com.springApp.dtos.UserDTO;
import com.springApp.entity.states.UserState;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.UserMapper;
import com.springApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper  userMapper;

    @PostMapping("/create/{rolId}")
    public ResponseEntity<?> createUser(
            @PathVariable Long rolId,
            @RequestBody UserDTO userDTO){
        try{
            UserDTO userCreated = userService.createuser(rolId, userDTO);
            return ResponseEntity.ok(userCreated);
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> userList(){
        List<UserDTO> userDTOS = userService.listUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/search/username/{username}")
    public ResponseEntity<?> searchByUsername(@PathVariable String username){
        Optional<UserDTO> userDTO = userService.searchByUsername(username);

        return userDTO.isPresent() ? ResponseEntity.ok(userDTO.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id){
        Optional<UserDTO> userDTO = userService.searchById(id);

        return userDTO.isPresent() ? ResponseEntity.ok(userDTO.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUsers(
            @PathVariable Long userId,
            @RequestBody UserDTO userDTO){
        try{
            UserDTO userUpdated = userService.updateUser(userId, userDTO);
            return  ResponseEntity.ok(userUpdated);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(e.getMessage());
        }
    }

    @PutMapping("/state/{userId}")
    public ResponseEntity<?> changeStateUser(
            @PathVariable Long userId,
            @RequestBody UserState userState){

        try{
            UserDTO userUpdated = userService.changeUserState(userId, userState);
            return ResponseEntity.ok(userUpdated);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<UserDTO>> listUserByState(@PathVariable UserState state){
        List<UserDTO> userDTO = userService.getUserByState(state);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update-password/{username}")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody UserDTO userDTO){
        try{
            userService.updatePassword(username, userDTO);
            return ResponseEntity.ok("Password Updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
