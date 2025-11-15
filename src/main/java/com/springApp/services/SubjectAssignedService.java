package com.springApp.services;

import com.springApp.dtos.SubjectAssignedDTO;
import com.springApp.dtos.SubjectAssignedResponseDTO;
import com.springApp.dtos.SubjectAssignedWithInscriptionsDTO;

import java.util.List;

public interface SubjectAssignedService {
    /**
     * Asigna una materia a un docente en un periodo específico
     * @param dto Datos de la asignación
     * @return DTO con la información de la asignación creada
     */
    SubjectAssignedResponseDTO assignSubject(SubjectAssignedDTO dto);

    /**
     * Actualiza una asignación de materia existente
     * @param id ID de la materia asignada
     * @param dto Datos actualizados de la asignación
     * @return DTO con la información actualizada
     */
    SubjectAssignedResponseDTO updateSubjectAssignment(Long id, SubjectAssignedDTO dto);

    /**
     * Obtiene todas las materias asignadas
     * @return Lista de todas las materias asignadas
     */
    List<SubjectAssignedResponseDTO> getAllSubjectAssignments();

    /**
     * Obtiene todas las materias asignadas en un periodo
     * @param periodId ID del periodo
     * @return Lista de materias asignadas
     */
    List<SubjectAssignedResponseDTO> getSubjectsByPeriod(Long periodId);

    /**
     * Obtiene solo las materias con cupo disponible en un periodo
     * @param periodId ID del periodo
     * @return Lista de materias con cupo disponible
     */
    List<SubjectAssignedResponseDTO> getAvailableSubjects(Long periodId);

    /**
     * Obtiene información básica de una materia asignada (sin inscripciones)
     * @param id ID de la materia asignada
     * @return DTO con información básica
     */
    SubjectAssignedResponseDTO getSubjectAssignedById(Long id);

    /**
     * Obtiene una materia asignada con todas sus inscripciones
     * @param id ID de la materia asignada
     * @return DTO con información completa incluyendo inscripciones
     */
    SubjectAssignedWithInscriptionsDTO getSubjectAssignedWithInscriptions(Long id);

    /**
     * Obtiene materias asignadas por docente
     * @param teacherId ID del docente
     * @return Lista de materias asignadas al docente
     */
    List<SubjectAssignedResponseDTO> getSubjectsByTeacher(Long teacherId);

    /**
     * Obtiene materias asignadas por aula
     * @param classroomId ID del aula
     * @return Lista de materias asignadas en el aula
     */
    List<SubjectAssignedResponseDTO> getSubjectsByClassroom(Long classroomId);

    /**
     * Actualiza el cupo disponible de una materia asignada
     * @param subjectAssignedId ID de la materia asignada
     * @param increase true para aumentar, false para disminuir
     */
    void updateAvailableSpace(Long subjectAssignedId, boolean increase);


}
