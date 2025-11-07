package com.springApp.services;

import com.springApp.dtos.InscriptionRequestDTO;
import com.springApp.dtos.InscriptionResponseDTO;

import java.util.List;

public interface InscriptionService {
    /**
     * Inscribe un estudiante en una materia asignada
     *
     * @param dto Datos de la inscripción
     * @return DTO con la información de la inscripción creada
     */
    List<InscriptionResponseDTO> enrollStudent(InscriptionRequestDTO dto);

    /**
     * Cancela una inscripción activa
     * @param inscriptionId ID de la inscripción
     */
    void cancelInscription(Long inscriptionId);

    /**
     * Obtiene todas las inscripciones de un estudiante
     * @param studentId ID del estudiante
     * @return Lista de inscripciones del estudiante
     */
    List<InscriptionResponseDTO> getStudentInscriptions(Long studentId);

    /**
     * Obtiene las inscripciones de un estudiante en un periodo específico
     * @param studentId ID del estudiante
     * @param periodId ID del periodo
     * @return Lista de inscripciones del estudiante en el periodo
     */
    List<InscriptionResponseDTO> getStudentInscriptionsByPeriod(Long studentId, Long periodId);

    /**
     * Obtiene una inscripción por su ID
     * @param inscriptionId ID de la inscripción
     * @return DTO con la información de la inscripción
     */
    InscriptionResponseDTO getInscriptionById(Long inscriptionId);

    /**
     * Obtiene el número de inscripciones activas de un estudiante en un periodo
     * @param studentId ID del estudiante
     * @param periodId ID del periodo
     * @return Número de inscripciones activas
     */
    Long countActiveInscriptionsByStudentAndPeriod(Long studentId, Long periodId);

    /**
     * Verifica si un estudiante puede inscribirse en una materia
     * @param studentId ID del estudiante
     * @param subjectAssignedId ID de la materia asignada
     * @return true si puede inscribirse, false en caso contrario
     */
    boolean canEnroll(Long studentId, Long subjectAssignedId);
}
