package com.springApp.services.impl;

import com.springApp.dtos.InscriptionRequestDTO;
import com.springApp.dtos.InscriptionResponseDTO;
import com.springApp.entity.InscriptionsEntity;
import com.springApp.entity.PeriodEntity;
import com.springApp.entity.StudentEntity;
import com.springApp.entity.SubjectAssignedEntity;
import com.springApp.entity.states.InscriptionStates;
import com.springApp.exception.BusinessException;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.repositories.InscriptionRepository;
import com.springApp.repositories.PeriodRepository;
import com.springApp.repositories.StudentRepository;
import com.springApp.repositories.SubjectAssignedRepository;
import com.springApp.services.InscriptionReportService;
import com.springApp.services.InscriptionService;
import com.springApp.services.SubjectAssignedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {
    private final InscriptionRepository inscriptionRepository;
    private final StudentRepository studentRepository;
    private final SubjectAssignedRepository subjectAssignedRepository;
    private final PeriodRepository periodRepository;
    private final SubjectAssignedService subjectAssignedService;
    private final InscriptionReportService inscriptionReportService;

    @Override
    @Transactional
    public List<InscriptionResponseDTO> enrollStudent(InscriptionRequestDTO dto) {
        log.info("Iniciando inscripción de estudiante: {}", dto);

        // 1. Validar que el estudiante existe
        StudentEntity student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estudiante no encontrado con ID: " + dto.getStudentId()));

        //  Validar que el periodo existe
        /* 2. Obtener fecha actual y el periodo activo automáticamente
        LocalDate today = LocalDate.now();
        PeriodEntity period = periodRepository.findCurrentPeriods(today)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un periodo académico activo en la fecha actual: " + today
                ));*/
        PeriodEntity period = periodRepository.findById(dto.getPeriodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Periodo no encontrado con ID: " + dto.getPeriodId()));



        // 2️. Crear una lista para devolver las inscripciones realizadas
        List<InscriptionResponseDTO> responses = new ArrayList<>();

        // 3️. Iterar sobre las materias a inscribir
        for (Long subjectAssignedId : dto.getSubjectsAssigned()) {
            try {
                // Llamamos a método interno transaccional independiente(enrollSingleSubject)
                InscriptionResponseDTO response = enrollSingleSubject(student, period, subjectAssignedId);
                responses.add(response);
            } catch (Exception e) {
                log.error("❌ Error al inscribir materia {}: {}", subjectAssignedId, e.getMessage());
            }
        }

        if (responses.isEmpty()) {
            throw new BusinessException("No se pudo inscribir ninguna materia (verificar cupos, duplicados o periodo)");
        }

        log.info("Inscripciones completadas: {} materias inscritas", responses.size());

        //  ⭐ GENERAR Y ENVIAR REPORTE PDF ⭐
        try {
            String reportPath = inscriptionReportService.generatePeriodReport(
                    student.getStudentId(),
                    period.getPeriodId()
            );
            log.info("Reporte PDF generado y enviado: {}", reportPath);
            log.info("✅ PDF generado y guardado en: {}", reportPath);
            log.info("📧 Email enviado a: {}", student.getEmail());
        } catch (Exception e) {
            log.error("Error al generar reporte PDF: {}", e.getMessage());
            // No interrumpir el proceso de inscripción si falla el reporte
        }


        return responses;
    }



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public InscriptionResponseDTO enrollSingleSubject(StudentEntity student, PeriodEntity period, Long subjectAssignedId) {
        // 4. Validar que la materia asignada existe
        SubjectAssignedEntity subjectAssigned = subjectAssignedRepository.findById(subjectAssignedId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia asignada no encontrada con ID: " + subjectAssignedId));

        // Validar que el estudiante ya esté inscrito
        if (inscriptionRepository.existsActiveInscription(student.getStudentId(), subjectAssignedId)) {
            throw new BusinessException(
                    "El estudiante ya está inscrito en esta materia: "
                            + subjectAssigned.getSubject().getName()
            );
        }

        // 5. Validar que el estudiante no esté ya inscrito
        if (inscriptionRepository.existsActiveInscription(student.getStudentId(), subjectAssignedId)) {
            throw new DuplicateResourceException("El estudiante ya está inscrito en esta materia");
        }

        // 6. Validar que el estudiante no haya completado ya esta materia
        if (inscriptionRepository.hasCompletedSubject(
                student.getStudentId(), subjectAssigned.getSubject().getSubjectId())) {
            throw new BusinessException("El estudiante ya ha aprobado esta materia");
        }

        // 7. Validar que la materia pertenece al periodo solicitado
        if (!subjectAssigned.getPeriod().getPeriodId().equals(period.getPeriodId())) {
            throw new BusinessException("La materia no pertenece al periodo seleccionado");
        }

        // 8. Validar que hay cupo disponible
        if (!subjectAssigned.tieneCupoDisponible()) {
            throw new BusinessException("La materia no tiene cupos disponibles");
        }

        // 9. Validar que el estudiante está en la misma carrera
        if (student.getCareer() != null &&
                subjectAssigned.getSubject().getCareer() != null &&
                !student.getCareer().getCareerId().equals(
                        subjectAssigned.getSubject().getCareer().getCareerId())) {
            throw new BusinessException("La materia no pertenece a la carrera del estudiante");
        }

        // Crear inscripción
        InscriptionsEntity inscription = new InscriptionsEntity();
        inscription.setStudent(student);
        inscription.setSubjectAssigned(subjectAssigned);
        inscription.setPeriod(period);
        inscription.setState(InscriptionStates.ACTIVE);

        //guardar en la DB
        InscriptionsEntity saved = inscriptionRepository.save(inscription);

        // Reducir cupo disponible
        subjectAssignedService.updateAvailableSpace(subjectAssignedId, false);

        log.info("✅ Inscripción individual completada: {}", saved.getInscriptionId());

        return mapToResponseDTO(saved);
        //return inscriptionMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void cancelInscription(Long inscriptionId) {
        log.info("Cancelando inscripción ID: {}", inscriptionId);

        InscriptionsEntity inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscripción no encontrada con ID: " + inscriptionId));

        if (!InscriptionStates.ACTIVE.equals(inscription.getState())) {
            throw new BusinessException(
                    "Solo se pueden cancelar inscripciones activas. Estado actual: " + inscription.getState());
        }

        // Cambiar estado a RETIRED
        inscription.setState(InscriptionStates.RETIRED);
        inscriptionRepository.save(inscription);

        // Aumentar el cupo disponible
        subjectAssignedService.updateAvailableSpace(
                inscription.getSubjectAssigned().getIdSubjectAssigned(), true);

        log.info("Inscripción cancelada exitosamente");

    }

    @Override
    @Transactional(readOnly = true)
    public List<InscriptionResponseDTO> getStudentInscriptions(Long studentId) {
        log.info("Obteniendo inscripciones del estudiante: {}", studentId);

        // Verificar que el estudiante existe
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Estudiante no encontrado con ID: " + studentId);
        }

        List<InscriptionsEntity> inscriptions =
                inscriptionRepository.findByStudentStudentId(studentId);

        return inscriptions.stream()
                .map(this::mapToResponseDTO)
                //.map(inscriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscriptionResponseDTO> getStudentInscriptionsByPeriod(Long studentId, Long periodId) {
        log.info("Obteniendo inscripciones del estudiante {} en el periodo {}", studentId, periodId);

        // Verificar que el estudiante existe
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Estudiante no encontrado con ID: " + studentId);
        }

        // Verificar que el periodo existe
        if (!periodRepository.existsById(periodId)) {
            throw new ResourceNotFoundException("Periodo no encontrado con ID: " + periodId);
        }

        List<InscriptionsEntity> inscriptions =
                inscriptionRepository.findByStudentAndPeriod(studentId, periodId);

        return inscriptions.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InscriptionResponseDTO getInscriptionById(Long inscriptionId) {
        log.info("Obteniendo inscripción con ID: {}", inscriptionId);

        InscriptionsEntity inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscripción no encontrada con ID: " + inscriptionId));

        return mapToResponseDTO(inscription);
    }

    /*Devuelve el número de materias en las que el estudiante está actualmente inscrito (activas) en un periodo específico.*/
    @Override
    @Transactional(readOnly = true)
    public Long countActiveInscriptionsByStudentAndPeriod(Long studentId, Long periodId) {
        log.info("Contando inscripciones activas del estudiante {} en periodo {}", studentId, periodId);

        List<InscriptionsEntity> inscriptions =
                inscriptionRepository.findByStudentAndPeriod(studentId, periodId);

        return inscriptions.stream()
                .filter(i -> InscriptionStates.ACTIVE.equals(i.getState()))
                .count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canEnroll(Long studentId, Long subjectAssignedId) {
        log.info("Verificando si estudiante {} puede inscribirse en materia asignada {}",
                studentId, subjectAssignedId);

        try {
            // Verificar que el estudiante existe
            StudentEntity student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

            // Verificar que la materia asignada existe
            SubjectAssignedEntity subjectAssigned = subjectAssignedRepository.findById(subjectAssignedId)
                    .orElseThrow(() -> new ResourceNotFoundException("Materia asignada no encontrada"));

            // Verificar si ya está inscrito
            if (inscriptionRepository.existsActiveInscription(studentId, subjectAssignedId)) {
                log.info("Estudiante ya está inscrito");
                return false;
            }

            // Verificar si ya completó la materia
            if (inscriptionRepository.hasCompletedSubject(
                    studentId, subjectAssigned.getSubject().getSubjectId())) {
                log.info("Estudiante ya completó la materia");
                return false;
            }

            // Verificar cupo disponible
            if (!subjectAssigned.tieneCupoDisponible()) {
                log.info("No hay cupo disponible");
                return false;
            }

            // Verificar carrera
            if (student.getCareer() != null &&
                    subjectAssigned.getSubject().getCareer() != null &&
                    !student.getCareer().getCareerId().equals(
                            subjectAssigned.getSubject().getCareer().getCareerId())) {
                log.info("Materia no pertenece a la carrera del estudiante");
                return false;
            }

            return true;

        } catch (Exception e) {
            log.error("Error al verificar si puede inscribirse: {}", e.getMessage());
            return false;
        }
    }

    private InscriptionResponseDTO mapToResponseDTO(InscriptionsEntity entity) {
        InscriptionResponseDTO dto = new InscriptionResponseDTO();

        // Información de la inscripción
        dto.setInscriptionId(entity.getInscriptionId());
        dto.setInscriptionDate(entity.getInscriptionDate().toString());
        dto.setState(entity.getState().name());

        // Información del estudiante
        dto.setStudentId(entity.getStudent().getStudentId());
        dto.setStudentName(entity.getStudent().getName() + " " + entity.getStudent().getLastname());
        dto.setStudentCarnet(entity.getStudent().getCarnet());
        dto.setStudentEmail(entity.getStudent().getEmail());

        // Información de la materia asignada
        dto.setSubjectAssignedId(entity.getSubjectAssigned().getIdSubjectAssigned());
        dto.setSubjectId(entity.getSubjectAssigned().getSubject().getSubjectId());
        dto.setSubjectCode(entity.getSubjectAssigned().getSubject().getSubjectCode());
        dto.setSubjectName(entity.getSubjectAssigned().getSubject().getName());
        dto.setValueUnits(entity.getSubjectAssigned().getSubject().getValueUnits());
        dto.setSection(entity.getSubjectAssigned().getSection());

        // Información del docente
        dto.setTeacherId(entity.getSubjectAssigned().getTeacher().getTeacherId());
        dto.setTeacherName(entity.getSubjectAssigned().getTeacher().getNames() + " " +
                entity.getSubjectAssigned().getTeacher().getLastName());
        dto.setTeacherCode(entity.getSubjectAssigned().getTeacher().getTeacherCode());

        // Información de horario
        dto.setScheduleId(entity.getSubjectAssigned().getSchedule().getScheduleId());
        dto.setScheduleDays(entity.getSubjectAssigned().getSchedule().getDays());
        dto.setScheduleTime(entity.getSubjectAssigned().getSchedule().getSchedule());

        // Información de aula
        dto.setClassroomId(entity.getSubjectAssigned().getClassroom().getClassroomId());
        dto.setClassroomName(entity.getSubjectAssigned().getClassroom().getName());
        dto.setBuilding(entity.getSubjectAssigned().getClassroom().getBuilding());

        // Información del periodo
        dto.setPeriodId(entity.getPeriod().getPeriodId());
        dto.setPeriodName(entity.getPeriod().getName());
        dto.setPeriodYear(entity.getPeriod().getYear());

        // Información de cupos
        dto.setAvailableSpace(entity.getSubjectAssigned().getAvailableSpace());
        dto.setMaximumCapacity(entity.getSubjectAssigned().getMaximumCapacity());

        return dto;
    }
}
