package com.springApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springApp.entity.states.UserState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String password;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name= "user_state", nullable = false )
    private UserState state;

    //Relation with RolEntity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", referencedColumnName = "rolId")
    private RolEntity roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StudentEntity student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TeacherEntity teacher;

    @JsonProperty("creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;

    @JsonProperty("updateDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private LocalDate updateDate;

    public UserEntity(String username, String password, String email, UserState state, RolEntity roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.state = state;
        this.roles = roles;
    }

    // =================================
    // Métodos de persistencia automática
    // =================================
    @PrePersist
    protected void onCreate() {
        creationDate = LocalDate.now();
        updateDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDate.now();
    }

}
