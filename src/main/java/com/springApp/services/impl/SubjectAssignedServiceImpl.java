package com.springApp.services.impl;

import com.springApp.dtos.*;
import com.springApp.entity.*;
import com.springApp.exception.*;
import com.springApp.mapper.SubjectAssignedResponseMapper;
import com.springApp.mapper.SubjectAssignedWithInscriptionsMapper;
import com.springApp.repositories.*;
import com.springApp.services.SubjectAssignedService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectAssignedServiceImpl implements SubjectAssignedService {
    //injection of dependency
    private final SubjectAssignedResponseMapper subjectAssignedResponse;
    private final SubjectAssignedWithInscriptionsMapper assignedWithInscriptionsMapper;
    private final SubjectAssignedRepository subjectAssignedRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final PeriodRepository periodRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClassroomRepository classroomRepository;
    private final InscriptionRepository inscriptionRepository;

    @Override
    @Transactional
    public SubjectAssignedResponseDTO assignSubject(SubjectAssignedDTO dto) {
        log.info("Iniciando asignación de materia: {}", dto);

        // 1. Validar que la materia existe
        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia no encontrada con ID: " + dto.getSubjectId()));

        // 2. Validar que el docente existe
        TeacherEntity teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Docente no encontrado con ID: " + dto.getTeacherId()));

        // 3. Validar que el periodo existe
        PeriodEntity period = periodRepository.findById(dto.getPeriodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Periodo no encontrado con ID: " + dto.getPeriodId()));

        // 4. Validar que el horario existe
        ScheduleEntity schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Horario no encontrado con ID: " + dto.getScheduleId()));

        // 5. Validar que el aula existe
        ClassroomEntity classroom = classroomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aula no encontrada con ID: " + dto.getClassroomId()));

        // 6. Validar que la capacidad no exceda la del aula
        if (dto.getMaximumCapacity() > classroom.getAbility()) {
            throw new BusinessException(
                    "La capacidad máxima (" + dto.getMaximumCapacity() +
                            ") excede la capacidad del aula (" + classroom.getAbility() + ")");
        }

        // 7. Validar que no exista una asignación duplicada
        if (subjectAssignedRepository.existsBySubjectAndTeacherAndPeriodAndSection(
                dto.getSubjectId(), dto.getTeacherId(), dto.getPeriodId(), dto.getSection())) {
            throw new DuplicateResourceException(
                    "Ya existe una asignación de esta materia con el mismo docente, periodo y sección");
        }

        // 8. Validar conflicto de horario del docente
        if (subjectAssignedRepository.existsTeacherScheduleConflict(
                dto.getTeacherId(), dto.getPeriodId(), dto.getScheduleId())) {
            throw new BusinessException(
                    "El docente ya tiene una clase asignada en este horario para este periodo");
        }

        // 9. Validar conflicto de aula
        if (subjectAssignedRepository.existsClassroomScheduleConflict(
                dto.getClassroomId(), dto.getPeriodId(), dto.getScheduleId())) {
            throw new BusinessException(
                    "El aula ya está ocupada en este horario para este periodo");
        }

        // 10. Crear la asignación
        SubjectAssignedEntity subjectAssigned = new SubjectAssignedEntity();
        subjectAssigned.setSubject(subject);
        subjectAssigned.setTeacher(teacher);
        subjectAssigned.setPeriod(period);
        subjectAssigned.setSchedule(schedule);
        subjectAssigned.setClassroom(classroom);
        subjectAssigned.setMaximumCapacity(dto.getMaximumCapacity());
        subjectAssigned.setAvailableSpace(dto.getMaximumCapacity());
        subjectAssigned.setSection(dto.getSection());

        SubjectAssignedEntity saved = subjectAssignedRepository.save(subjectAssigned);
        log.info("Materia asignada exitosamente con ID: {}", saved.getIdSubjectAssigned());

        return subjectAssignedResponse.toDTO(saved);
    }

    @Override
    @Transactional
    public SubjectAssignedResponseDTO updateSubjectAssignment(Long id, SubjectAssignedDTO dto) {
        log.info("Actualizando asignación de materia ID: {}", id);

        // Validar que la asignación existe
        SubjectAssignedEntity subjectAssigned = subjectAssignedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia asignada no encontrada con ID: " + id));

        // Validar que la materia existe
        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia no encontrada con ID: " + dto.getSubjectId()));

        // Validar que el docente existe
        TeacherEntity teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Docente no encontrado con ID: " + dto.getTeacherId()));

        // Validar que el periodo existe
        PeriodEntity period = periodRepository.findById(dto.getPeriodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Periodo no encontrado con ID: " + dto.getPeriodId()));

        // Validar que el horario existe
        ScheduleEntity schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Horario no encontrado con ID: " + dto.getScheduleId()));

        // Validar que el aula existe
        ClassroomEntity classroom = classroomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aula no encontrada con ID: " + dto.getClassroomId()));

        // Validar que la capacidad no exceda la del aula
        if (dto.getMaximumCapacity() > classroom.getAbility()) {
            throw new BusinessException(
                    "La capacidad máxima (" + dto.getMaximumCapacity() +
                            ") excede la capacidad del aula (" + classroom.getAbility() + ")");
        }

        // Validar conflicto de horario del docente (excluyendo la asignación actual)
        List<SubjectAssignedEntity> teacherAssignments = subjectAssignedRepository
                .findByTeacherAndPeriod(dto.getTeacherId(), dto.getPeriodId());

        boolean hasConflict = teacherAssignments.stream()
                .filter(sa -> !sa.getIdSubjectAssigned().equals(id))
                .anyMatch(sa -> sa.getSchedule().getScheduleId().equals(dto.getScheduleId()));

        if (hasConflict) {
            throw new BusinessException(
                    "El docente ya tiene una clase asignada en este horario para este periodo");
        }

        // Validar conflicto de aula (excluyendo la asignación actual)
        List<SubjectAssignedEntity> classroomAssignments = subjectAssignedRepository
                .findByClassroomClassroomId(dto.getClassroomId());

        boolean hasClassroomConflict = classroomAssignments.stream()
                .filter(sa -> !sa.getIdSubjectAssigned().equals(id))
                .filter(sa -> sa.getPeriod().getPeriodId().equals(dto.getPeriodId()))
                .anyMatch(sa -> sa.getSchedule().getScheduleId().equals(dto.getScheduleId()));

        if (hasClassroomConflict) {
            throw new BusinessException(
                    "El aula ya está ocupada en este horario para este periodo");
        }

        // Contar estudiantes inscritos actualmente
        Long enrolledCount = inscriptionRepository.countActiveInscriptionsBySubjectAssigned(id);

        // Validar que la nueva capacidad no sea menor que los inscritos actuales
        if (dto.getMaximumCapacity() < enrolledCount.intValue()) {
            throw new BusinessException(
                    "No se puede reducir la capacidad máxima a " + dto.getMaximumCapacity() +
                            " porque ya hay " + enrolledCount + " estudiantes inscritos");
        }

        // Actualizar campos
        subjectAssigned.setSubject(subject);
        subjectAssigned.setTeacher(teacher);
        subjectAssigned.setPeriod(period);
        subjectAssigned.setSchedule(schedule);
        subjectAssigned.setClassroom(classroom);
        subjectAssigned.setMaximumCapacity(dto.getMaximumCapacity());
        subjectAssigned.setSection(dto.getSection());

        // Recalcular cupo disponible
        int newAvailableSpace = dto.getMaximumCapacity() - enrolledCount.intValue();
        subjectAssigned.setAvailableSpace(newAvailableSpace);

        SubjectAssignedEntity updated = subjectAssignedRepository.save(subjectAssigned);
        log.info("Materia asignada actualizada: ID {}, nuevo cupo disponible: {}",
                updated.getIdSubjectAssigned(), updated.getAvailableSpace());

        return subjectAssignedResponse.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAssignedResponseDTO> getAllSubjectAssignments() {
        log.info("Obteniendo todas las materias asignadas");
        List<SubjectAssignedEntity> subjects = subjectAssignedRepository.findAll();
        return subjects.stream()
                .map(subjectAssignedResponse::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAssignedResponseDTO> getSubjectsByPeriod(Long periodId) {
        log.info("Obteniendo materias asignadas del periodo: {}", periodId);
        List<SubjectAssignedEntity> subjects = subjectAssignedRepository.findByPeriodPeriodId(periodId);
        return subjects.stream()
                .map(subjectAssignedResponse::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectAssignedResponseDTO> getAvailableSubjects(Long periodId) {
        log.info("Obteniendo materias disponibles del periodo: {}", periodId);
        List<SubjectAssignedEntity> subjects = subjectAssignedRepository.findAvailableByPeriod(periodId);
        return subjects.stream()
                .map(subjectAssignedResponse::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public SubjectAssignedResponseDTO getSubjectAssignedById(Long id) {
        log.info("Obteniendo materia asignada con ID: {}", id);
        SubjectAssignedEntity subject = subjectAssignedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia asignada no encontrada con ID: " + id));
        return subjectAssignedResponse.toDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectAssignedWithInscriptionsDTO getSubjectAssignedWithInscriptions(Long id) {
        log.info("Obteniendo materia asignada con inscripciones, ID: {}", id);

        SubjectAssignedEntity subject = subjectAssignedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia asignada no encontrada con ID: " + id));
        return assignedWithInscriptionsMapper.toDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAssignedResponseDTO> getSubjectsByTeacher(Long teacherId) {
        log.info("Obteniendo materias del docente: {}", teacherId);
        List<SubjectAssignedEntity> subjects = subjectAssignedRepository.findByTeacherTeacherId(teacherId);
        return subjects.stream()
                .map(subjectAssignedResponse::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAssignedResponseDTO> getSubjectsByClassroom(Long classroomId) {
        log.info("Obteniendo materias del aula: {}", classroomId);
        List<SubjectAssignedEntity> subjects = subjectAssignedRepository.findByClassroomClassroomId(classroomId);
        return subjects.stream()
                .map(subjectAssignedResponse::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAvailableSpace(Long subjectAssignedId, boolean increase) {
        log.info("Actualizando cupo disponible para materia asignada ID: {}, increase: {}",
                subjectAssignedId, increase);

        SubjectAssignedEntity subject = subjectAssignedRepository.findById(subjectAssignedId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia asignada no encontrada con ID: " + subjectAssignedId));

        if (increase) {
            subject.aumentarCupo();
            log.info("Cupo aumentado. Nuevo cupo disponible: {}", subject.getAvailableSpace());
        } else {
            if (!subject.tieneCupoDisponible()) {
                throw new BusinessException("No hay cupo disponible para reducir");
            }
            subject.reducirCupo();
            log.info("Cupo reducido. Nuevo cupo disponible: {}", subject.getAvailableSpace());
        }

        subjectAssignedRepository.save(subject);
    }



    // ============================================
    // MÉTODOS PRIVADOS DE MAPEO
    // ============================================
/*
    private SubjectAssignedResponseDTO mapToResponseDTO(SubjectAssignedEntity entity) {
        SubjectAssignedResponseDTO dto = new SubjectAssignedResponseDTO();
        dto.setSubjectAssignedId(entity.getIdSubjectAssigned());
        dto.setSubjectCode(entity.getSubject().getSubjectCode());
        dto.setSubjectName(entity.getSubject().getName());
        dto.setTeacherName(entity.getTeacher().getNames() + " " + entity.getTeacher().getLastName());
        dto.setSchedule(entity.getSchedule().getDays() + " " + entity.getSchedule().getSchedule());
        dto.setClassroom(entity.getClassroom().getName() + " - " + entity.getClassroom().getBuilding());
        dto.setSection(entity.getSection());
        dto.setAvailableSpace(entity.getAvailableSpace());
        dto.setMaximumCapacity(entity.getMaximumCapacity());
        dto.setPeriod(entity.getPeriod().getName());
        return dto;
    }

    private SubjectAssignedWithInscriptionsDTO mapToResponseWithInscriptionsDTO(SubjectAssignedEntity entity) {
        SubjectAssignedWithInscriptionsDTO dto = new SubjectAssignedWithInscriptionsDTO();
        dto.setSubjectAssignedId(entity.getIdSubjectAssigned());
        dto.setSubjectCode(entity.getSubject().getSubjectCode());
        dto.setSubjectName(entity.getSubject().getName());
        dto.setTeacherName(entity.getTeacher().getNames() + " " + entity.getTeacher().getLastName());
        dto.setSchedule(entity.getSchedule().getDays() + " " + entity.getSchedule().getSchedule());
        dto.setClassroom(entity.getClassroom().getName() + " - " + entity.getClassroom().getBuilding());
        dto.setSection(entity.getSection());
        dto.setPeriodName(entity.getPeriod().getName());
        dto.setAvailableSpace(entity.getAvailableSpace());
        dto.setMaximumCapacity(entity.getMaximumCapacity());

        // Mapear inscripciones
        if (entity.getInscriptions() != null) {
            List<InscriptionSummaryDTO> inscriptions = entity.getInscriptions().stream()
                    .map(this::mapInscriptionToSummary)
                    .collect(Collectors.toList());
            dto.setInscriptions(inscriptions);
        } else {
            dto.setInscriptions(new ArrayList<>());
        }

        return dto;
    }

    private InscriptionSummaryDTO mapInscriptionToSummary(InscriptionsEntity inscription) {
        InscriptionSummaryDTO dto = new InscriptionSummaryDTO();
        dto.setInscriptionId(inscription.getInscriptionId());
        dto.setStudentId(inscription.getStudent().getStudentId());
        dto.setStudentName(inscription.getStudent().getName() + " " + inscription.getStudent().getLastname());
        dto.setStudentCarnet(inscription.getStudent().getCarnet());
        dto.setStudentEmail(inscription.getStudent().getEmail());
        dto.setInscriptionDate(inscription.getInscriptionDate().toString());
        dto.setState(inscription.getState().name());
        return dto;
    }*/
}
