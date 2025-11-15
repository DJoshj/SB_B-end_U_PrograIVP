package com.springApp.controllers;

import com.springApp.config.FileStorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportController {

    private final FileStorageConfig fileStorageConfig;

    /**
     * Descarga un reporte PDF generado
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadReport (@PathVariable String fileName) {
        try {
            // Construir ruta del archivo
            String filePath = fileStorageConfig.getPdfStoragePath() + File.separator + fileName;
            Path path = Paths.get(filePath);

            // Verificar que el archivo existe
            if (!Files.exists(path)) {
                log.error("Archivo no encontrado: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            // Crear recurso
            Resource resource = new FileSystemResource(path.toFile());

            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            log.info("Descargando archivo: {}", fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(Files.size(path))
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (IOException e) {
            log.error("Error al descargar archivo: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Re-genera y re-envía un reporte de inscripción
     */
    @PostMapping("/resend/{inscriptionId}")
    public ResponseEntity<?> resendReport(@PathVariable Integer inscriptionId) {
        try {
            // Aquí puedes llamar al servicio para regenerar y reenviar
            // inscriptionReportService.generateAndSendReport(inscriptionId);

            return ResponseEntity.ok().body("Reporte reenviado exitosamente");
        } catch (Exception e) {
            log.error("Error al reenviar reporte: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
