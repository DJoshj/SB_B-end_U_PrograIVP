package com.springApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springApp.entity.states.InscriptionStates;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "inscriptions")
public class InscriptionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inscriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject_assigned", nullable = false)
    private SubjectAssignedEntity subjectAssigned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_period", nullable = false)
    private PeriodEntity period;

    @JsonProperty("creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "inscription_date", nullable = false, updatable = false)
    private LocalDate inscriptionDate;


    @Enumerated(EnumType.STRING)
    @Column(name= "inscription_state", nullable = false )
    private InscriptionStates state; // ACTIVO, RETIRADO, COMPLETADO

    @PrePersist
    protected void onCreate() {
        inscriptionDate = LocalDate.now();
    }

}
