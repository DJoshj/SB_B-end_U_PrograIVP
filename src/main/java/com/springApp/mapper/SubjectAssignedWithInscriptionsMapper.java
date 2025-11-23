package com.springApp.mapper;

import com.springApp.dtos.InscriptionSummaryDTO;
import com.springApp.dtos.SubjectAssignedWithInscriptionsDTO;
import com.springApp.entity.InscriptionsEntity;
import com.springApp.entity.SubjectAssignedEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectAssignedWithInscriptionsMapper {


    public SubjectAssignedWithInscriptionsDTO toDTO(SubjectAssignedEntity entity) {

        SubjectAssignedWithInscriptionsDTO dto = new SubjectAssignedWithInscriptionsDTO();
        dto.setSubjectAssignedId(entity.getIdSubjectAssigned());

        // Datos de la materia
        dto.setSubjectCode(entity.getSubject().getSubjectCode());
        dto.setSubjectName(entity.getSubject().getName());

        // Nombre completo del docente
        dto.setTeacherName(
                entity.getTeacher().getNames() + " " + entity.getTeacher().getLastName()
        );

        // Horario mostrado en texto
        dto.setSchedule(
                entity.getSchedule().getDays() + " " +
                        entity.getSchedule().getSchedule()
        );

        // Aula
        dto.setClassroom(entity.getClassroom().getName());

        // Sección
        dto.setSection(entity.getSection());

        // Periodo
        dto.setPeriodName(entity.getPeriod().getName());

        // Espacios
        dto.setMaximumCapacity(entity.getMaximumCapacity());
        dto.setAvailableSpace(entity.getAvailableSpace());

        // ---- INSCRIPCIONES ----
        if (entity.getInscriptions() != null) {
            List<InscriptionSummaryDTO> inscriptionDTOs = entity.getInscriptions()
                    .stream()
                    .map(this::mapInscription)
                    .toList();

            dto.setInscriptions(inscriptionDTOs);
        } else {
            dto.setInscriptions(List.of());
        }

        return dto;
    }

    // Conversión manual de Inscription → InscriptionSummaryDTO
    private InscriptionSummaryDTO mapInscription(InscriptionsEntity ins) {
        InscriptionSummaryDTO dto = new InscriptionSummaryDTO();

        dto.setInscriptionId(ins.getInscriptionId());
        dto.setStudentId(ins.getStudent().getStudentId());
        dto.setStudentName(ins.getStudent().getName() + " " + ins.getStudent().getLastname());
        dto.setStudentCarnet(ins.getStudent().getCarnet());
        dto.setStudentEmail(ins.getStudent().getEmail());
        dto.setInscriptionDate(ins.getInscriptionDate().toString());
        dto.setState(ins.getState().name());

        return dto;
    }
}



