package com.springApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "teacher")
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    //relation with user
    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @NotNull(message = "enter a code of teacher")
    @Column(name = "teacher_code",nullable = false, length = 20)
    private String teacherCode;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String names;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Email
    @NotBlank
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String specialty;

    //relation with subjectAssigned
    @OneToMany(mappedBy = "teacher")
    private List<SubjectAssignedEntity> subjectAssigned;

}
