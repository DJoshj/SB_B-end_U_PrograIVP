package com.springApp.services.impl;

import com.springApp.dtos.InscriptionReportDTO;
import com.springApp.entity.InscriptionsEntity;
import com.springApp.entity.PeriodEntity;
import com.springApp.entity.StudentEntity;
import com.springApp.entity.SubjectAssignedEntity;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.repositories.InscriptionRepository;
import com.springApp.repositories.StudentRepository;
import com.springApp.services.EmailService;
import com.springApp.services.InscriptionReportService;
import com.springApp.services.PdfGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InscriptionReportServiceImpl implements InscriptionReportService {
    private final InscriptionRepository inscriptionRepository;
    private final StudentRepository studentRepository;
    private final PdfGeneratorService pdfGeneratorService;
    private final EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public String generateAndSendReport(Long inscriptionId) {
        log.info("Generando reporte para inscripción ID: {}", inscriptionId);

        // Obtener inscripción
        InscriptionsEntity inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscripción no encontrada con ID: " + inscriptionId));

        // Obtener todas las inscripciones del estudiante en el mismo periodo
        List<InscriptionsEntity> allInscriptions = inscriptionRepository
                .findByStudentAndPeriod(
                        inscription.getStudent().getStudentId(),
                        inscription.getPeriod().getPeriodId()
                );

        return generateAndSendReportInternal(inscription.getStudent(), allInscriptions);
    }

    @Override
    @Transactional(readOnly = true)
    public String generatePeriodReport(Long studentId, Long periodId) {
        log.info("Generando reporte de periodo para estudiante ID: {}, periodo: {}", studentId, periodId);

        // Obtener estudiante
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estudiante no encontrado con ID: " + studentId));

        // Obtener inscripciones
        List<InscriptionsEntity> inscriptions = inscriptionRepository
                .findByStudentAndPeriod(studentId, periodId);

        if (inscriptions.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No se encontraron inscripciones para el estudiante en este periodo");
        }

        return generateAndSendReportInternal(student, inscriptions);
    }

    private String generateAndSendReportInternal(StudentEntity student,
                                                 List<InscriptionsEntity> inscriptions) {
        try {
            // Construir DTO con los datos
            InscriptionReportDTO reportData = buildReportData(student, inscriptions);

            // Generar PDF
            byte[] pdfContent = pdfGeneratorService.generateInscriptionReport(reportData);

            // Generar nombre de archivo
            String fileName = generateFileName(student.getCarnet(),
                    inscriptions.get(0).getPeriod().getName());

            // Guardar PDF
            String filePath = pdfGeneratorService.savePdf(pdfContent, fileName);

            // Enviar email
            sendReportEmail(student.getEmail(), reportData.getStudentName(),
                    pdfContent, fileName);

            return filePath;

        } catch (Exception e) {
            log.error("Error al generar y enviar reporte: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte de inscripción", e);
        }
    }

    private InscriptionReportDTO buildReportData(StudentEntity student,
                                                 List<InscriptionsEntity> inscriptions) {
        PeriodEntity period = inscriptions.get(0).getPeriod();

        // Generar clave de inscripción (ejemplo: 4EBE91DD-F)
        String inscriptionKey = generateInscriptionKey(student.getCarnet());

        // Construir lista de materias
        List<InscriptionReportDTO.SubjectInscriptionDetail> subjects = inscriptions.stream()
                .map(this::mapToSubjectDetail)
                .collect(Collectors.toList());

        return InscriptionReportDTO.builder()
                .studentName(student.getName() + " " + student.getLastname())
                .carnet(student.getCarnet())
                .career(student.getCareer() != null ? student.getCareer().getNameCareer() : "N/A")
                .plan(student.getCareer().getPlan().toString())
                .inscriptionKey(inscriptionKey)
                .inscriptionDate(inscriptions.get(0).getInscriptionDate())
                .classStartDate(period.getStartDate())
                .subjects(subjects)
                .facultyContactEmail("edwin.Salomon@ulaes.edu.sv")
                .virtualClassroomContact("yanira.Rivas@ulaes.edu.sv")
                .academicAdministrationContact("academica.enlinea@ulaes.edu.sv")
                .build();
    }

    private InscriptionReportDTO.SubjectInscriptionDetail mapToSubjectDetail(InscriptionsEntity inscription) {
        SubjectAssignedEntity sa = inscription.getSubjectAssigned();

        return InscriptionReportDTO.SubjectInscriptionDetail.builder()
                .code(sa.getSubject().getSubjectCode())
                .name(sa.getSubject().getName())
                .section(sa.getSection())
                .matricula(inscription.getInscriptionId())
                .days(sa.getSchedule().getDays())
                .schedule(sa.getSchedule().getSchedule())
                .classroom(sa.getClassroom().getName())
                .build();
    }

    private String generateInscriptionKey(String carnet) {

        String timestamp = String.valueOf(System.currentTimeMillis());

        // Generar hash hexadecimal largo (mínimo 16 chars)
        String hash = Integer.toHexString((carnet + timestamp).hashCode()).toUpperCase();

        // Asegurar longitud mínima de 9
        while (hash.length() < 9) {
            hash += "A"; // relleno simple
        }

        return hash.substring(0, 8) + "-" + hash.substring(8, 9);
    }


    private String generateFileName(String carnet, String periodName) {
        String sanitizedPeriod = periodName.replaceAll("[^a-zA-Z0-9]", "_");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("inscripcion_%s_%s_%s.pdf", carnet, sanitizedPeriod, date);
    }

    private void sendReportEmail(String toEmail, String studentName,
                                 byte[] pdfContent, String fileName) {
        try {
            String subject = "Comprobante de Inscripción - ULAES";

            String body = String.format(
                    "<html>" +
                            "<body style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color: #8B0000;'>Universidad Latinoamericana de Educación Superior</h2>" +
                            "<p>Estimado/a <strong>%s</strong>,</p>" +
                            "<p>Adjunto encontrará su comprobante de inscripción para el ciclo actual.</p>" +
                            "<p>Este documento contiene:</p>" +
                            "<ul>" +
                            "<li>Información de las materias inscritas</li>" +
                            "<li>Horarios y aulas asignadas</li>" +
                            "<li>Información de contacto</li>" +
                            "</ul>" +
                            "<p><strong>Importante:</strong> Conserve este documento para futuras referencias.</p>" +
                            "<p>Para cualquier consulta, puede contactar a:</p>" +
                            "<ul>" +
                            "<li>Administración Académica: academica.enlinea@ulaes.edu.sv</li>" +
                            "<li>Portal Educativo: <a href='https://portal.ulaes.edu.sv'>https://portal.ulaes.edu.sv</a></li>" +
                            "</ul>" +
                            "<p>Fecha de inicio de clases: Consulte el calendario académico.</p>" +
                            "<br>" +
                            "<p>Atentamente,</p>" +
                            "<p><strong>Administración Académica</strong><br>" +
                            "Universidad Latinoamericana de Educación Superior</p>" +
                            "</body>" +
                            "</html>",
                    studentName
            );

            emailService.sendEmailWithAttachment(toEmail, subject, body, pdfContent, fileName);
            log.info("Email de reporte enviado exitosamente a: {}", toEmail);

        } catch (Exception e) {
            log.error("Error al enviar email de reporte: {}", e.getMessage(), e);
            // No lanzar excepción para no interrumpir el proceso de inscripción
        }
    }


}
