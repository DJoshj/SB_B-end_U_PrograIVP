package com.springApp.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolDTO {
    private Long rolId;

    @NotBlank
    @Column
    private String name;
}
