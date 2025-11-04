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
@Table(name = "subject_assigned")
public class SubjectAssignedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSubjectAssigned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject", nullable = false)
    private SubjectEntity subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_teacher", nullable = false)
    private TeacherEntity teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_period", nullable = false)
    private PeriodEntity period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_schedule", nullable = false)
    private ScheduleEntity schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_classroom", nullable = false)
    private ClassroomEntity classroom;

    @NotBlank(message = "Enter a number maximum of student")
    @Column(name = "maximum_capacity", nullable = false)
    private Integer maximumCapacity;

    @NotBlank(message = "Enter an available space")
    @Column(name = "available_space", nullable = false)
    private Integer availableSpace;

    @NotBlank(message = "Enter a name of the section")
    @Column(length = 10)
    private String section;

    @OneToMany(mappedBy = "subjectAssigned", cascade = CascadeType.ALL)
    private List<InscriptionsEntity> inscriptions;

    // Método auxiliar para verificar disponibilidad
    public boolean tieneCupoDisponible() {
        return availableSpace > 0;
    }

    // Método para reducir cupo
    public void reducirCupo() {
        if (availableSpace > 0) {
            this.availableSpace--;
        }
    }

    // Método para aumentar cupo (cancelación)
    public void aumentarCupo() {
        if (availableSpace < maximumCapacity) {
            this.availableSpace++;
        }
    }

}
