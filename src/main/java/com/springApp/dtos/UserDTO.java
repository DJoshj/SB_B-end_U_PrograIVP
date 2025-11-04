package com.springApp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springApp.entity.RolEntity;
import com.springApp.entity.states.UserState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long userId;

    @NotBlank(message = "El usuario es requerido")
    @Size(min = 4, max = 50, message = "El usuario debe tener entre 4 y 50 caracteres")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @NotNull
    private String password;

    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es requerido")
    @Size(max = 80)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name= "user_state", nullable = false )
    private UserState state;

    private RolEntity roles;

    @JsonProperty("creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;

    @JsonProperty("updateDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private LocalDate updateDate;
}
