package com.springApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @NotBlank(message = "enter a carnet of student")
    @Column(nullable = false, length = 25)
    private String carnet;

    @NotBlank(message = "enter a name of student")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "enter a lastname of student")
    @Column(name = "last_name",nullable = false, length = 100)
    private String lastname;

    @Email
    @NotBlank(message = "enter an email of student")
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank(message = "enter a number phone")
    @Column(nullable = false, length = 100)
    private String phone;

    @NotBlank(message = "enter an address")
    @Column(nullable = false, length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_career")
    private CareerEntity career;

    @OneToMany(mappedBy = "student")
    private List<InscriptionsEntity> inscriptions;

    public StudentEntity(String carnet, String name, String lastname, String email, String phone, String address, CareerEntity career, UserEntity user) {
        this.carnet = carnet;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.career = career;
        this.user = user;
    }
}
