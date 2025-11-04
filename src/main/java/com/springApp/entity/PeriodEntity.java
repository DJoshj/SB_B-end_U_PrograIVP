package com.springApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "period")
public class PeriodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long periodId;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;  //ej. CICLO 2 - 2025

    @Column(nullable = false, length = 100)
    private Integer year;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String schedule;

    @OneToMany(mappedBy = "period")
    private List<SubjectAssignedEntity> subjectAssigned;

    @OneToMany(mappedBy = "period")
    private List<InscriptionsEntity> inscriptions;

}
