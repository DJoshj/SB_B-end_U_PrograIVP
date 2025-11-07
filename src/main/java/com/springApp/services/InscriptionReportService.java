package com.springApp.services;

public interface InscriptionReportService {
    /**
     * Genera y envía el reporte de inscripción por email
     * @param inscriptionId ID de la inscripción
     * @return Ruta del PDF guardado
     */
    String generateAndSendReport(Long inscriptionId);

    /**
     * Genera el reporte de inscripción para múltiples materias
     * @param studentId ID del estudiante
     * @param periodId ID del periodo
     * @return Ruta del PDF guardado
     */
    String generatePeriodReport(Long studentId, Long periodId);
}
