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

    private Integer plan;

    @NotBlank(message = "The faculty can't be empty")
    @Column(nullable = false, length = 100)
    private String faculty;

    @Column(name = "faculty_code", length = 10)
    private String facultyCode;


    //mappedBy = "carrera" es el nombre del atributo de la otra clase de la relación
    @OneToMany(mappedBy = "career")
    private List<StudentEntity> student;

    @OneToMany(mappedBy = "career")
    private List<SubjectEntity> subject;

    public CareerEntity(String nameCareer, Integer plan, String faculty, String facultyCode) {
        this.nameCareer = nameCareer;
        this.plan = plan;
        this.faculty = faculty;
        this.facultyCode = facultyCode;
    }

    public CareerEntity(String nameCareer, Integer plan, String faculty) {
        this.nameCareer = nameCareer;
        this.plan = plan;
        this.faculty = faculty;
    }


}
