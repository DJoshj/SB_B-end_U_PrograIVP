package com.springApp.services.impl;

import com.springApp.dtos.*;
import com.springApp.entity.*;
import com.springApp.exception.*;
import com.springApp.mapper.SubjectAssignedMapper;
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
    private SubjectAssignedMapper assignedMapper;
    private final SubjectAssignedRepository subjectAssignedRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final PeriodRepository periodRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClassroomRepository classroomRepository;

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

        return assignedMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAssignedResponseDTO> getSubjectsByPeriod(Long periodId) {
        log.info("Obteniendo materias asignadas del periodo: {}", periodId);
        List<SubjectAssignedEntity> subjects = subjectAssignedRepository.findByPeriodPeriodId(periodId);
        return subjects.stream()
                .map(assignedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectAssignedResponseDTO> getAvailableSubjects(Long periodId) {
        log.info("Obteniendo materias disponibles del periodo: {}", periodId);
        List<SubjectAssignedEntity> subjects = subjectAssignedRepository.findAvailableByPeriod(periodId);
        return subjects.stream()
                .map(assignedMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public SubjectAssignedResponseDTO getSubjectAssignedById(Integer id) {
        return null;
    }

    @Override
    public SubjectAssignedWithInscriptionsDTO getSubjectAssignedWithInscriptions(Integer id) {
        return null;
    }

    @Override
    public List<SubjectAssignedResponseDTO> getSubjectsByTeacher(Long teacherId) {
        return List.of();
    }

    @Override
    public List<SubjectAssignedResponseDTO> getSubjectsByClassroom(Integer classroomId) {
        return List.of();
    }

    @Override
    public void updateAvailableSpace(Integer subjectAssignedId, boolean increase) {

    }
}
