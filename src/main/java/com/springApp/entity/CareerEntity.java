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
@Table(name = "career")
public class CareerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    @NotBlank(message = "The name of career can´t be empty")
    @Column(nullable = false, length = 100)
    private String nameCareer;

    @NotBlank(message = "The faculty can't be empty")
    @Column(nullable = false, length = 100)
    private String faculty;

    //mappedBy = "carrera" es el nombre del atributo de la otra clase de la relación
    @OneToMany(mappedBy = "career")
    private List<StudentEntity> student;

    @OneToMany(mappedBy = "career")
    private List<SubjectEntity> subject;

}
