package com.springApp.services;

import jakarta.mail.MessagingException;

// ============================================
// 6. SERVICIO DE ENVÍO DE EMAIL
// ============================================
public interface EmailService {
    /**
     * Envía un email con un archivo adjunto
     * @param to Destinatario
     * @param subject Asunto
     * @param body Cuerpo del mensaje
     * @param attachment Contenido del archivo adjunto
     * @param attachmentName Nombre del archivo adjunto
     */
    void sendEmailWithAttachment(String to, String subject, String body,
                                 byte[] attachment, String attachmentName) throws MessagingException;
}
