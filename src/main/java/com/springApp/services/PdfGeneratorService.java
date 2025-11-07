package com.springApp.services;

import com.springApp.dtos.InscriptionReportDTO;

// ============================================
//       SERVICIO DE GENERACIÓN DE PDF
// ============================================
public interface PdfGeneratorService {
    /**
     * Genera un PDF con el reporte de inscripción
     * @param reportData Datos del reporte
     * @return Array de bytes del PDF generado
     */
    byte[] generateInscriptionReport(InscriptionReportDTO reportData);

    /**
     * Guarda el PDF en el sistema de archivos
     * @param pdfContent Contenido del PDF
     * @param fileName Nombre del archivo
     * @return Ruta del archivo guardado
     */
    String savePdf(byte[] pdfContent, String fileName);

    /**
     * Obtiene la URL pública del PDF
     * @param fileName Nombre del archivo
     * @return URL completa del PDF
     */
    String getPdfUrl(String fileName);
}
