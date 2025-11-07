package com.springApp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    @NotNull(message = "El ID del usuario es requerido")
    private Long userId;

    @NotBlank(message = "El código de docente es requerido")
    private String teacherCode;

    @NotBlank(message = "Los nombres son requeridos")
    private String names;

    @NotBlank(message = "Los apellidos son requeridos")
    private String lastName;

    @NotBlank(message = "El email es requerido")
    @Email
    private String email;

    @NotBlank(message = "La especialidad es requerida")
    private String specialty;
}
