package com.springApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


// ============================================
//          DTO PARA DATOS DEL REPORTE
// ============================================

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionReportDTO {
    // Información del estudiante
    private String studentName;
    private String carnet;
    private String career;
    private String plan;

    // Información de inscripción
    private String inscriptionKey;
    private LocalDate inscriptionDate;
    private LocalDate classStartDate;

    // Lista de materias inscritas
    private List<SubjectInscriptionDetail> subjects;

    // Contactos
    private String facultyContactEmail;
    private String virtualClassroomContact;
    private String academicAdministrationContact;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubjectInscriptionDetail {
        private String code;
        private String name;
        private String section;
        private Long matricula;
        private String days;
        private String schedule;
        private String classroom;
    }
}
