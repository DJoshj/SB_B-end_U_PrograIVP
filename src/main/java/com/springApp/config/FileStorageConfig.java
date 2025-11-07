package com.springApp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

// ============================================
//       CONFIGURACIÓN DE DIRECTORIOS
// ============================================
@Configuration
public class FileStorageConfig {
    @Value("${app.pdf.storage.path:reportes/inscripciones}")
    private String pdfStoragePath;

    @PostConstruct
    public void init() {
        File directory = new File(pdfStoragePath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Directorio de PDFs creado: " + pdfStoragePath);
            }
        }
    }

    public String getPdfStoragePath() {
        return pdfStoragePath;
    }
}
