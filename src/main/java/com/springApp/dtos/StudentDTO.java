package com.springApp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private UserDTO user;

    @NotBlank(message = "El carnet es requerido")
    @Size(max = 25)
    private String carnet;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "El apellido es requerido")
    @Size(max = 100)
    private String lastname;

    @NotBlank(message = "El email es requerido")
    @Email
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    private String phone;

    @NotBlank(message = "La dirección es requerida")
    private String address;

    private Long careerId;
}
