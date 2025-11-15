package com.springApp.services.impl;

import com.springApp.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailUlaes;

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment, String attachmentName) throws MessagingException {
        log.info("Enviando email a: {}", to);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");



            helper.setFrom(emailUlaes);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            if (attachment != null && attachment.length > 0) {
                helper.addAttachment(attachmentName, new ByteArrayResource(attachment));
            }

            mailSender.send(message);
            log.info("Email enviado exitosamente a: {}", to);

        } catch (MessagingException e) {
            log.error("Error al enviar email: {}", e.getMessage(), e);
            throw e;
        }
    }

}
