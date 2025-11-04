package com.springApp.entity;

import jakarta.persistence.*;
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
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @NotNull(message = "enter a code of the subject")
    @Column(nullable = false, unique = true, length = 20)
    private String subjectCode;

    @NotBlank(message = "enter a name of the subject")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private Integer valueUnits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_career")
    private CareerEntity career;

    @OneToMany(mappedBy = "subject")
    private List<SubjectAssignedEntity> subjectAssigned;

}
