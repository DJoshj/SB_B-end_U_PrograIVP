package com.springApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "rols", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name") // 👈 evita duplicados en BD
})
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    @NotBlank(message = "the rol name cannot be empty")
    @Column
    private String name;

    public RolEntity(String name) {
        this.name = name;
    }
}
