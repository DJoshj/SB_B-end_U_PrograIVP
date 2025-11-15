package com.springApp.controllers;

import com.springApp.dtos.RolDTO;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.UserMapper;
import com.springApp.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RolController {
    @Autowired
    private RolService rolService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createRol(@RequestBody  RolDTO rolDTO){
        try{
            RolDTO rolCreated = rolService.createRol(rolDTO);
            return ResponseEntity.ok(rolCreated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<RolDTO>> rolesList(){
        List<RolDTO> rolesDTOS = rolService.listRoles();
        return ResponseEntity.ok(rolesDTOS);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id){
        Optional<RolDTO> rolDTO = rolService.getRolById(id);

        return rolDTO.isPresent() ? ResponseEntity.ok(rolDTO.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/update/{rolId}")
    public ResponseEntity<?> updateRoles(
            @PathVariable Long rolId,
            @RequestBody RolDTO rolDTO){
        try{
            RolDTO rolesUpdated = rolService.updateRol(rolId, rolDTO);
            return  ResponseEntity.ok(rolesUpdated);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{rolId}")
    public ResponseEntity<?> deleteRoles(@PathVariable Long rolId){
        try{
            rolService.deleteRol(rolId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(e.getMessage());
        }
    }
}
