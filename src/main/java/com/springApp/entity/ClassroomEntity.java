package com.springApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "classroom")
public class ClassroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    @NotBlank(message = "the name of classroom can´t be empty")
    @Column(nullable = false, length = 50)
    private String name;

    private Integer ability;

    @NotBlank(message = "Enter a building")
    @Column(length = 50)
    private String building;

    @OneToMany(mappedBy = "classroom")
    private List<SubjectAssignedEntity> subjectAssigned;

    public ClassroomEntity(String name, Integer ability, String building) {
        this.name = name;
        this.ability = ability;
        this.building = building;
    }
}
