package com.springApp.dtos;

import com.springApp.entity.RolEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
public class RolDTO {
    private Long rolId;

    @NotBlank
    @Column
    private String name;
}
