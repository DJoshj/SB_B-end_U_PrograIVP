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
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @NotBlank(message = "enter a days of the classes. ej lun-mar")
    @Column(name = "days", nullable = false, length = 25)
    private String days;

    @NotBlank(message = "enter a schedules of the classes. ej. 06:30-8:30")
    @Column(nullable = false, length = 25)
    private String schedule;

    @OneToMany(mappedBy = "schedule")
    private List<SubjectAssignedEntity> subjectAssigns;

    public ScheduleEntity(String days, String schedule) {
        this.days = days;
        this.schedule = schedule;
    }
}
