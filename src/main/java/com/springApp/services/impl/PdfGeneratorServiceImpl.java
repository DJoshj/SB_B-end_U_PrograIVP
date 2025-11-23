package com.springApp.services.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.springApp.config.FileStorageConfig;
import com.springApp.dtos.InscriptionReportDTO;
import com.springApp.services.PdfGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfGeneratorServiceImpl implements PdfGeneratorService {
    private final FileStorageConfig fileStorageConfig;

    @Value("${app.pdf.base-url:http://localhost:8080}")
    private String baseUrl;

    @Override
    public byte[] generateInscriptionReport(InscriptionReportDTO reportData) {
        log.info("Generando reporte PDF para estudiante: {}", reportData.getStudentName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Configurar fuentes
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // ========== ENCABEZADO ==========
            addHeader(document, bold);

            document.add(new Paragraph("\n"));

            // ========== TÍTULO ==========
            Paragraph title = new Paragraph("Comprobante de materias inscritas para el ciclo: 01-2026")
                    .setFont(bold)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // ========== INFORMACIÓN DEL ESTUDIANTE ==========
            addStudentInfo(document, reportData, bold, regular);

            document.add(new Paragraph("\n"));

            // ========== TABLA DE MATERIAS ==========
            addSubjectsTable(document, reportData, bold, regular);

            document.add(new Paragraph("\n"));

            // ========== INFORMACIÓN DE INSCRIPCIÓN ==========
            addInscriptionInfo(document, reportData, bold, regular);

            document.add(new Paragraph("\n"));

            // ========== INICIO DE CLASES ==========
            Paragraph classStart = new Paragraph("INICIO DE CLASES: " +
                    reportData.getClassStartDate().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy")))
                    .setFont(bold)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold();
            document.add(classStart);

            document.add(new Paragraph("\n"));

            // ========== NOTAS IMPORTANTES ==========
            addImportantNotes(document, regular);

            document.add(new Paragraph("\n"));

            // ========== TABLA DE CONTACTOS ==========
            addContactsTable(document, reportData, bold, regular);

            document.add(new Paragraph("\n"));

            // ========== PIE DE PÁGINA ==========
            addFooter(document, regular);

            document.close();

            log.info("PDF generado exitosamente para {}", reportData.getStudentName());
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Error al generar PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte PDF", e);
        }
    }
    private void addHeader(Document document, PdfFont bold) throws IOException {
        // Tabla para el encabezado (logo + texto)
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{20, 80}));
        headerTable.setWidth(UnitValue.createPercentValue(100));

        // Logo (si existe)
        try {
            // Aquí debes colocar la ruta de tu logo
            Image logo = new Image(ImageDataFactory.create("src/main/resources/static/img/img.png"));
            logo.setWidth(80);
            headerTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));

            // Si no hay logo, deja la celda vacía
            //headerTable.addCell(new Cell().setBorder(Border.NO_BORDER));
        } catch (Exception e) {
            log.warn("No se pudo cargar el logo");
            headerTable.addCell(new Cell().setBorder(Border.NO_BORDER));
        }

        // Texto del encabezado
        Paragraph headerText = new Paragraph()
                .add(new Text("Universidad Latinoamérica\n").setFont(bold).setFontSize(16).setFontColor(new DeviceRgb(139, 0, 0)))
                .add(new Text("de Educación Superior").setFont(bold).setFontSize(14).setFontColor(new DeviceRgb(139, 0, 0)))
                .setTextAlignment(TextAlignment.CENTER);

        headerTable.addCell(new Cell().add(headerText).setBorder(Border.NO_BORDER));

        document.add(headerTable);
    }
    private void addStudentInfo(Document document, InscriptionReportDTO reportData,
                                PdfFont bold, PdfFont regular) {
        Paragraph studentInfo = new Paragraph()
                .add(new Text(reportData.getStudentName().toUpperCase() + "\n").setFont(bold).setFontSize(10))
                .add(new Text("Carnet: " + reportData.getCarnet() + "\n").setFont(regular).setFontSize(9))
                .add(new Text("Carrera: " + reportData.getCareer() + ", Plan: " + reportData.getPlan())
                        .setFont(regular).setFontSize(9));

        document.add(studentInfo);
    }
    private void addSubjectsTable(Document document, InscriptionReportDTO reportData,
                                  PdfFont bold, PdfFont regular) {
        // Crear tabla de materias
        float[] columnWidths = {10, 25, 10, 10, 15, 15, 15};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));


        // Encabezados
        String[] headers = {"Código", "Materia", "Sección", "Matrícula", "Días", "Hora", "Aula"};
        for (String header : headers) {
            Cell cell = new Cell()
                    .add(new Paragraph(header).setFont(bold).setFontSize(9))
                    .setBackgroundColor(new DeviceRgb(211, 211, 211))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5);
            table.addHeaderCell(cell);
        }

        // Agregar materias
        for (InscriptionReportDTO.SubjectInscriptionDetail subject : reportData.getSubjects()) {
            table.addCell(createCell(subject.getCode(), regular, TextAlignment.CENTER));
            table.addCell(createCell(subject.getName(), regular, TextAlignment.LEFT));
            table.addCell(createCell(subject.getSection(), regular, TextAlignment.CENTER));
            table.addCell(createCell("1", regular, TextAlignment.CENTER));                      ///NUMERO DE MATRICULA
            table.addCell(createCell(subject.getDays(), regular, TextAlignment.CENTER));
            table.addCell(createCell(subject.getSchedule(), regular, TextAlignment.CENTER));
            table.addCell(createCell(subject.getClassroom(), regular, TextAlignment.CENTER));
        }

        document.add(table);
    }
    private Cell createCell(String content, PdfFont font, TextAlignment alignment) {
        return new Cell()
                .add(new Paragraph(content).setFont(font).setFontSize(8))
                .setTextAlignment(alignment)
                .setPadding(3);
    }
    private void addInscriptionInfo(Document document, InscriptionReportDTO reportData,
                                    PdfFont bold, PdfFont regular) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma", Locale.US);

        Paragraph inscriptionInfo = new Paragraph()
                .add(new Text("Clave de inscripción: ").setFont(bold).setFontSize(9))
                .add(new Text(reportData.getInscriptionKey() + ", ").setFont(regular).setFontSize(9))
                .add(new Text("Fecha de inscripción: ").setFont(bold).setFontSize(9))
                .add(new Text(now.format(formatter)).setFont(regular).setFontSize(9))
                .setTextAlignment(TextAlignment.CENTER);

        document.add(inscriptionInfo);
    }
    private void addImportantNotes(Document document, PdfFont regular) {
        Paragraph notes = new Paragraph()
                .add(new Text("Para consultas de notas, correo institucional, documentos de clase ingrese al portal educativo: ")
                        .setFont(regular).setFontSize(8))
                .add(new Text("https://portal.ulaes.edu.sv\n").setFont(regular).setFontSize(8).setFontColor(ColorConstants.BLUE))
                .add(new Text("El correo que la universidad le proporciona como estudiante es exclusivamente de uso institucional.")
                        .setFont(regular).setFontSize(8).setBold());

        document.add(notes);
    }
    private void addContactsTable(Document document, InscriptionReportDTO reportData,
                                  PdfFont bold, PdfFont regular) {
        // Título
        Paragraph contactTitle = new Paragraph("Contacto de tu facultad para actualizar el acceso a la plataforma teams")
                .setFont(bold)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(5);
        document.add(contactTitle);

        // Tabla de contactos
        Table contactTable = new Table(UnitValue.createPercentArray(new float[]{15, 40, 15, 30}));
        contactTable.setWidth(UnitValue.createPercentValue(100));

        // Headers
        contactTable.addHeaderCell(createCell("Código", bold, TextAlignment.CENTER)
                .setBackgroundColor(new DeviceRgb(211, 211, 211)));
        contactTable.addHeaderCell(createCell("Materia", bold, TextAlignment.CENTER)
                .setBackgroundColor(new DeviceRgb(211, 211, 211)));
        contactTable.addHeaderCell(createCell("Sección", bold, TextAlignment.CENTER)
                .setBackgroundColor(new DeviceRgb(211, 211, 211)));
        contactTable.addHeaderCell(createCell("Correo", bold, TextAlignment.CENTER)
                .setBackgroundColor(new DeviceRgb(211, 211, 211)));

        // Agregar datos de contacto por materia
        for (InscriptionReportDTO.SubjectInscriptionDetail subject : reportData.getSubjects()) {
            contactTable.addCell(createCell(subject.getCode(), regular, TextAlignment.CENTER));
            contactTable.addCell(createCell(subject.getName(), regular, TextAlignment.LEFT));
            contactTable.addCell(createCell(subject.getSection(), regular, TextAlignment.CENTER));
            contactTable.addCell(createCell(reportData.getFacultyContactEmail(), regular, TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE));
        }

        document.add(contactTable);

        // Contactos adicionales
        document.add(new Paragraph("\n"));

        Paragraph additionalContacts = new Paragraph()
                .add(new Text("Contacto para actualizar acceso a las aulas virtuales y de apoyo\n")
                        .setFont(bold).setFontSize(9))
                .add(new Text(reportData.getVirtualClassroomContact() + "\n\n")
                        .setFont(regular).setFontSize(8).setFontColor(ColorConstants.BLUE))
                .add(new Text("Contacto con administración académica\n")
                        .setFont(bold).setFontSize(9))
                .add(new Text(reportData.getAcademicAdministrationContact())
                        .setFont(regular).setFontSize(8).setFontColor(ColorConstants.BLUE));

        document.add(additionalContacts);
    }
    private void addFooter(Document document, PdfFont regular) {
        Paragraph footer = new Paragraph(
                "Nota: Favor escribir al correo electrónico correspondiente, solamente en caso de presentar " +
                        "dificultades para ingresar a la plataforma Teams o plataforma de aula virtual o Moodle."
        )
                .setFont(regular)
                .setFontSize(7)
                .setItalic()
                .setTextAlignment(TextAlignment.CENTER);

        document.add(footer);
    }

    @Override
    public String savePdf(byte[] pdfContent, String fileName) {
        try {
            String fullPath = fileStorageConfig.getPdfStoragePath() + File.separator + fileName;
            File file = new File(fullPath);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(pdfContent);
            }

            log.info("PDF guardado en: {}", fullPath);
            return fullPath;

        } catch (IOException e) {
            log.error("Error al guardar PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar el PDF", e);
        }
    }

    @Override
    public String getPdfUrl(String fileName) {
        return baseUrl + "/api/reports/download/" + fileName;
    }
}
