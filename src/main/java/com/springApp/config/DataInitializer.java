package com.springApp.config;

import com.springApp.entity.*;
import com.springApp.entity.states.UserState;
import com.springApp.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private SubjectAssignedRepository subjectAssignedRepository;
    @Autowired
    private InscriptionRepository inscriptionRepository;


    @Override
    public void run(String... args) throws Exception {
        /*========================
            -----Roles------
         ========================
        * */

        if (rolRepository.count() == 0) {
            logger.info("🔄 Creating  Roles...");

            rolRepository.save(new RolEntity("admin"));
            rolRepository.save(new RolEntity("teacher"));
            rolRepository.save(new RolEntity("student"));
            rolRepository.save(new RolEntity("staff"));
            rolRepository.save(new RolEntity("finance"));

            logger.info("✅ Roles Created Successfully::::Mysql");
            logger.info("📊 Total roles: " + rolRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Roles. Total  roles: " + rolRepository.count());
        }

        /*========================
            -----Users------
         ========================
        *

        if (userRepository.count() == 0) {
            logger.info("🔄 Creating  Users...");

            userRepository.save( new UserEntity(
                    "Alfonso",
                    passwordEncoder.encode("admin"),
                    "admin.rivera@gmail.com",
                    UserState.ACTIVE,
                    new RolEntity(1L,"admin")
            ));

            userRepository.save( new UserEntity(
                    "Elida",
                    passwordEncoder.encode("elida"),
                    "elida.estefany@gmail.com",
                    UserState.ACTIVE,
                    new RolEntity(2L, "teacher")
            ));

            userRepository.save( new UserEntity(
                    "Kaisy",
                    passwordEncoder.encode("kaisy"),
                    "kaisy.ramos@gmail.com",
                    UserState.ACTIVE,
                    new RolEntity(3L, "student")
            )); 

            logger.info("✅ Users Created Successfully::::Mysql");
            logger.info("📊 Total Users: " + userRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Users. Total users: " + userRepository.count());
        }*/

        /*========================
            -----Career------
         ========================
        * */
        if (careerRepository.count() == 0) {
            logger.info("🔄 Creating  Careers...");

            careerRepository.save( new CareerEntity("Ingeniería en Sistemas", 2022, "Ingeniería y Arquitectura", "15"));//15
            careerRepository.save( new CareerEntity("Ingeniería Electrica",2022,  "Ingeniería y Arquitectura", "15"));
            careerRepository.save( new CareerEntity("Ingeniería Informatica",2022,  "Ingeniería y Arquitectura", "15"));
            careerRepository.save( new CareerEntity("Ingeniería Industrial",2022,  "Ingeniería y Arquitectura", "15"));
            careerRepository.save( new CareerEntity("Ingeniería Electronica",2022,  "Ingeniería y Arquitectura", "15"));
            careerRepository.save( new CareerEntity("Ingeniería Mecanica",2022,  "Ingeniería y Arquitectura", "15"));
            careerRepository.save( new CareerEntity("Arquitectura",2023, "Ingeniería y Arquitectura", "17"));   //17
            careerRepository.save( new CareerEntity("Administración de Empresas",2020, "Ciencias Económicas", "19")); //19
            careerRepository.save( new CareerEntity("Ciencias de la Comunicación",2020, "Ciencias Económicas", "19"));
            careerRepository.save( new CareerEntity("Contaduría Pública",2021, "Ciencias Económicas", "19"));
            careerRepository.save( new CareerEntity("Derecho",2021, "Ciencias Jurídicas", "21"));      //21
            careerRepository.save( new CareerEntity("Diseño Gráfico",2021, "Artes y Diseño", "23"));  //23
            careerRepository.save( new CareerEntity("Psicología",2023, "Humanidades", "25"));  //25

            logger.info("✅ Careers Created Successfully::::Mysql");
            logger.info("📊 Total Career: " + careerRepository.count());
        } else {
            logger.info("ℹ️ DB already contains careers. Total careers: " + careerRepository.count());
        }

        /*========================
            -----Students------
         ========================
        * */
        if (studentRepository.count() == 0) {
            logger.info("🔄 Creating  Students...");
            RolEntity rolStudent = rolRepository.findByName("student")
                    .orElseThrow(() -> new RuntimeException("Rol 'Student' no encontrado"));
            CareerEntity career1 = careerRepository.findById(1L).get(); // Ingeniería en Sistemas
            CareerEntity career2 = careerRepository.findById(2L).get(); // Ingeniería Eléctrica
            CareerEntity career3 = careerRepository.findById(3L).get(); // Ingeniería Informática
            CareerEntity career4 = careerRepository.findById(4L).get(); // Ingeniería Industrial
            CareerEntity career5 = careerRepository.findById(5L).get(); // Ingeniería Electrónica
            CareerEntity career6 = careerRepository.findById(6L).get(); // Ingeniería Mecánica
            CareerEntity career7 = careerRepository.findById(7L).get(); // Arquitectura
            CareerEntity career8 = careerRepository.findById(8L).get(); // Administración de Empresas
            CareerEntity career9 = careerRepository.findById(9L).get(); // Ciencias de la Comunicación
            CareerEntity career10 = careerRepository.findById(10L).get(); // Contaduría Pública
            CareerEntity career11 = careerRepository.findById(11L).get(); // Derecho

            // ==================== ESTUDIANTES ====================
            studentRepository.save(new StudentEntity("25-0124-2025", "Jose Manuel", "Rodriguez Chavez", "jrodriguez@gmail.com", "7410-2589", "San Miguel",
                    career1,
                    new UserEntity("JManuel", passwordEncoder.encode("jManuel"), "jrodriguez@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("25-0126-2025", "Luis Fernando", "Alas Pineda", "lalas@gmail.com", "7788-9966", "Santa Ana",
                    career2,
                    new UserEntity("Fernando", passwordEncoder.encode("Fernando"), "lalas@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("25-0127-2025", "Ana Cristina", "Mendoza Diaz", "amendoza@gmail.com", "6021-5478", "Ahuachapán",
                    career3,
                    new UserEntity("Cristina", passwordEncoder.encode("Cristina"), "amendoza@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("25-0132-2025", "Kaisy Alejandra", "Cardona Ramos", "kaisy.ramos@gmail.com", "7156-6608", "San Marcos",
                    career4,
                    new UserEntity("Alejandra", passwordEncoder.encode("Alejandra"), "kaisy.ramos@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("25-0128-2025", "Ricardo Ariel", "Ventura Perez", "rventura@email.com", "7369-8521", "Usulután",
                    career5,
                    new UserEntity("Ariel", passwordEncoder.encode("Ariel"), "rventura@email.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("25-0129-2025", "Laura Patricia", "Castillo Rivas", "lcastillo@gmail.com", "6987-4512", "Chalatenango",
                    career6,
                    new UserEntity("Patricia", passwordEncoder.encode("Patricia"), "lcastillo@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("25-0130-2025", "David Alejandro", "Martinez Cruz", "dmartinez@gmail.com", "7123-6540", "Morazán",
                    career7,
                    new UserEntity("Alejandro", passwordEncoder.encode("Alejandro"), "dmartinez@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("17-0131-2025", "Pamela Nicole", "Rivera Morales", "privera@gmail.com", "6230-1478", "La Paz",
                    career8,
                    new UserEntity("Nicole", passwordEncoder.encode("Nicole"), "privera@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("17-0132-2025", "Javier Ernesto", "Guardado Linares", "jguardado@gmail.com", "7541-8963", "Cuscatlán",
                    career9,
                    new UserEntity("Ernesto", passwordEncoder.encode("Ernesto"), "jguardado@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("17-0133-2025", "Karina Fabiola", "Escobar Orellana", "kescobar@gmail.com", "6874-1230", "Cabañas",
                    career10,
                    new UserEntity("Karina", passwordEncoder.encode("Karina"), "kescobar@gmail.com", UserState.ACTIVE, rolStudent)));

            studentRepository.save(new StudentEntity("19-0141-2025", "Claudia Elena", "Miranda Segovia", "cmiranda@gmail.com", "6698-7452", "San Marcos",
                    career11,
                    new UserEntity("Elena", passwordEncoder.encode("Elena"), "cmiranda@gmail.com", UserState.ACTIVE, rolStudent)));



            logger.info("✅ Students Created Successfully::::Mysql");
            logger.info("📊 Total Students: " + studentRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Students. Total Students: " + studentRepository.count());
        }

        /*========================
            -----Subjects------
         ========================
        * */

        if (subjectRepository.count() == 0) {
            logger.info("🔄 Creating  Subjects...");
            // ==============================
            // FACULTAD: Ingeniería y Arquitectura
            // ==============================

            // Ingeniería en Sistemas
            CareerEntity career1 = careerRepository.findById(1L).get();
            subjectRepository.save(new SubjectEntity("PROG1", "Programación I", 4, career1));
            subjectRepository.save(new SubjectEntity("BD1", "Base de Datos I", 4, career1));
            subjectRepository.save(new SubjectEntity("RED1", "Redes de Computadoras I", 3, career1));
            subjectRepository.save(new SubjectEntity("SO1", "Sistemas Operativos", 4, career1));

            // Ingeniería Eléctrica
            CareerEntity career2 = careerRepository.findById(2L).get();
            subjectRepository.save(new SubjectEntity("ELEC1", "Circuitos Eléctricos I", 4, career2));
            subjectRepository.save(new SubjectEntity("ELEC2", "Electromagnetismo", 3, career2));
            subjectRepository.save(new SubjectEntity("AUTO1", "Automatización Industrial", 4, career2));
            subjectRepository.save(new SubjectEntity("MATE1", "Matemática Aplicada", 3, career2));

            // Ingeniería Informática
            CareerEntity career3 = careerRepository.findById(3L).get();
            subjectRepository.save(new SubjectEntity("PROG2", "Programación II", 4, career3));
            subjectRepository.save(new SubjectEntity("WEB1", "Desarrollo Web", 4, career3));
            subjectRepository.save(new SubjectEntity("SEG1", "Seguridad Informática", 3, career3));
            subjectRepository.save(new SubjectEntity("RED2", "Redes de Computadoras II", 3, career3));

            // Ingeniería Industrial
            CareerEntity career4 = careerRepository.findById(4L).get();
            subjectRepository.save(new SubjectEntity("INDU1", "Procesos Industriales", 4, career4));
            subjectRepository.save(new SubjectEntity("LOG1", "Logística Industrial", 3, career4));
            subjectRepository.save(new SubjectEntity("CAL1", "Control de Calidad", 3, career4));
            subjectRepository.save(new SubjectEntity("PROD1", "Gestión de Producción", 4, career4));

            // Ingeniería Electrónica
            CareerEntity career5 = careerRepository.findById(5L).get();
            subjectRepository.save(new SubjectEntity("DIGI1", "Electrónica Digital", 4, career5));
            subjectRepository.save(new SubjectEntity("MICRO1", "Microcontroladores", 4, career5));
            subjectRepository.save(new SubjectEntity("ANA1", "Electrónica Analógica", 4, career5));
            subjectRepository.save(new SubjectEntity("SEN1", "Sensores y Actuadores", 3, career5));

            // Ingeniería Mecánica
            CareerEntity career6 = careerRepository.findById(6L).get();
            subjectRepository.save(new SubjectEntity("TER1", "Termodinámica", 4, career6));
            subjectRepository.save(new SubjectEntity("MAT1", "Mecánica de Materiales", 4, career6));
            subjectRepository.save(new SubjectEntity("DIS1", "Diseño Mecánico", 4, career6));
            subjectRepository.save(new SubjectEntity("MAN1", "Mantenimiento Industrial", 3, career6));

            // Arquitectura
            CareerEntity career7 = careerRepository.findById(7L).get();
            subjectRepository.save(new SubjectEntity("ARQ1", "Diseño Arquitectónico I", 4, career7));
            subjectRepository.save(new SubjectEntity("HIS1", "Historia de la Arquitectura", 3, career7));
            subjectRepository.save(new SubjectEntity("CONS1", "Construcción I", 4, career7));
            subjectRepository.save(new SubjectEntity("REP1", "Representación Gráfica", 3, career7));


            // ==============================
            // FACULTAD: Ciencias Económicas
            // ==============================

            // Administración de Empresas
            CareerEntity career8 = careerRepository.findById(8L).get();
            subjectRepository.save(new SubjectEntity("ADM1", "Administración I", 4, career8));
            subjectRepository.save(new SubjectEntity("FIN1", "Finanzas I", 3, career8));
            subjectRepository.save(new SubjectEntity("MKT1", "Mercadeo I", 3, career8));
            subjectRepository.save(new SubjectEntity("RH1", "Recursos Humanos I", 4, career8));

            // Ciencias de la Comunicación
            CareerEntity career9 = careerRepository.findById(9L).get();
            subjectRepository.save(new SubjectEntity("COM1", "Teoría de la Comunicación", 3, career9));
            subjectRepository.save(new SubjectEntity("PER1", "Periodismo Digital", 4, career9));
            subjectRepository.save(new SubjectEntity("PUB1", "Publicidad y Medios", 3, career9));
            subjectRepository.save(new SubjectEntity("AUD1", "Producción Audiovisual", 4, career9));

            // Contaduría Pública
            CareerEntity career10 = careerRepository.findById(10L).get();
            subjectRepository.save(new SubjectEntity("CON1", "Contabilidad I", 4, career10));
            subjectRepository.save(new SubjectEntity("AUD2", "Auditoría I", 4, career10));
            subjectRepository.save(new SubjectEntity("IMP1", "Legislación Tributaria", 3, career10));
            subjectRepository.save(new SubjectEntity("FIN2", "Finanzas Corporativas", 4, career10));


            // ==============================
            // FACULTAD: Ciencias Jurídicas
            // ==============================

            // Derecho
            CareerEntity career11 = careerRepository.findById(11L).get();
            subjectRepository.save(new SubjectEntity("DER1", "Derecho Constitucional", 4, career11));
            subjectRepository.save(new SubjectEntity("PEN1", "Derecho Penal I", 4, career11));
            subjectRepository.save(new SubjectEntity("CIV1", "Derecho Civil I", 4, career11));
            subjectRepository.save(new SubjectEntity("MER1", "Derecho Mercantil", 3, career11));


            // ==============================
            // FACULTAD: Artes y Diseño
            // ==============================

            // Diseño Gráfico
            CareerEntity career12 = careerRepository.findById(12L).get();
            subjectRepository.save(new SubjectEntity("DISG1", "Diseño Digital", 4, career12));
            subjectRepository.save(new SubjectEntity("TIP1", "Tipografía", 3, career12));
            subjectRepository.save(new SubjectEntity("ILU1", "Ilustración", 4, career12));
            subjectRepository.save(new SubjectEntity("FOT1", "Fotografía Digital", 3, career12));


            // ==============================
            // FACULTAD: Humanidades
            // ==============================

            // Psicología
            CareerEntity career13 = careerRepository.findById(13L).get();
            subjectRepository.save(new SubjectEntity("PSI1", "Psicología General", 4, career13));
            subjectRepository.save(new SubjectEntity("PSI2", "Psicología del Desarrollo", 3, career13));
            subjectRepository.save(new SubjectEntity("CLI1", "Psicología Clínica", 4, career13));
            subjectRepository.save(new SubjectEntity("SOC1", "Psicología Social", 3, career13));

            logger.info("✅ Subjects Created Successfully::::Mysql");
            logger.info("📊 Total Subjects: " + subjectRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Subjects. Total Subjects: " + subjectRepository.count());
        }

        /*========================
            -----Teacher------
         ========================
        * */

        if (teacherRepository.count() == 0) {
            logger.info("🔄 Creating  Teachers...");
            RolEntity rolTeacher = rolRepository.findByName("teacher")
                    .orElseThrow(() -> new RuntimeException("Rol 'Student' no encontrado"));

            // ==================== DOCENTES ====================
            teacherRepository.save(new TeacherEntity(
                    "T0001", "Kaisy Alejandra", "Cardona Ramos", "kaisy.cardona@gmail.com", "Ingeniería de Software",
                    new UserEntity("Cardona", passwordEncoder.encode("kaisy"), "kaisy.cardona@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0002", "Carlos Alberto", "Mendoza López", "cmendoza@gmail.com", "Bases de Datos",
                    new UserEntity("Mendoza", passwordEncoder.encode("carlos"), "cmendoza@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0003", "Patricia Elena", "Hernández Campos", "phernandez@gmail.com", "Redes y Telecomunicaciones",
                    new UserEntity("PHernandez", passwordEncoder.encode("patricia"), "phernandez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0004", "José Armando", "Rivas Mejía", "jrivas@gmail.com", "Ingeniería Eléctrica",
                    new UserEntity("Rivas", passwordEncoder.encode("armando"), "jrivas@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0005", "María Fernanda", "Gómez Rivera", "mgomez@gmail.com", "Ingeniería Industrial",
                    new UserEntity("Gomez", passwordEncoder.encode("fernanda"), "mgomez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0006", "Ricardo Enrique", "Martínez Calderón", "rmartinez@gmail.com", "Arquitectura de Computadores",
                    new UserEntity("RMartinez", passwordEncoder.encode("ricardo"), "rmartinez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0007", "Laura Patricia", "Vargas Castillo", "lvargas@gmail.com", "Arquitectura y Diseño",
                    new UserEntity("Vargas", passwordEncoder.encode("laura"), "lvargas@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0008", "Francisco Javier", "López Hernández", "fjlopez@gmail.com", "Administración Financiera",
                    new UserEntity("FJLópez", passwordEncoder.encode("francisco"), "fjlopez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0009", "Claudia Beatriz", "Ortiz Melgar", "cortiz@gmail.com", "Ciencias de la Comunicación",
                    new UserEntity("COrtiz", passwordEncoder.encode("claudia"), "cortiz@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0010", "Luis Ernesto", "García Bonilla", "lgarcia@gmail.com", "Contabilidad y Finanzas",
                    new UserEntity("LGarcia", passwordEncoder.encode("luis"), "lgarcia@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0011", "Verónica del Carmen", "Chávez Ayala", "vchavez@gmail.com", "Derecho Penal",
                    new UserEntity("VChavez", passwordEncoder.encode("veronica"), "vchavez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0012", "David Alejandro", "Ramos Aguilar", "dramos@gmail.com", "Sistemas Embebidos",
                    new UserEntity("DRamos", passwordEncoder.encode("david"), "dramos@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0013", "Sofía Carolina", "Escobar Linares", "sescobar@gmail.com", "Ingeniería Mecánica",
                    new UserEntity("SEscobar", passwordEncoder.encode("sofia"), "sescobar@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0014", "Fernando José", "Campos Pineda", "fcampos@gmail.com", "Automatización Industrial",
                    new UserEntity("FCampos", passwordEncoder.encode("fernando"), "fcampos@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0015", "Isabel Raquel", "Mejía Torres", "imejia@gmail.com", "Electrónica Analógica",
                    new UserEntity("IMejia", passwordEncoder.encode("isabel"), "imejia@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0016", "Óscar Armando", "Pérez Alvarado", "operez@gmail.com", "Matemática Aplicada",
                    new UserEntity("OPerez", passwordEncoder.encode("oscar"), "operez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0017", "Gabriela Elena", "López Portillo", "glopez@gmail.com", "Estadística Computacional",
                    new UserEntity("GLopez", passwordEncoder.encode("gabriela"), "glopez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0018", "Mario Antonio", "Quintanilla Reyes", "mquintanilla@gmail.com", "Desarrollo Web",
                    new UserEntity("MQuintanilla", passwordEncoder.encode("mario"), "mquintanilla@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0019", "Karla Ivonne", "Ruiz Salinas", "kruiz@gmail.com", "Ingeniería de Datos",
                    new UserEntity("KRuiz", passwordEncoder.encode("karla"), "kruiz@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0020", "Javier Eduardo", "Velásquez Herrera", "jvelasquez@gmail.com", "Inteligencia Artificial",
                    new UserEntity("JVelasquez", passwordEncoder.encode("javier"), "jvelasquez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0021", "Andrés Felipe", "Torres Molina", "atorres@gmail.com", "Diseño Gráfico Digital",
                    new UserEntity("ATorres", passwordEncoder.encode("andres"), "atorres@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0022", "Roxana Beatriz", "Morales Fuentes", "rmorales@gmail.com", "Psicología Clínica",
                    new UserEntity("RMorales", passwordEncoder.encode("roxana"), "rmorales@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0023", "Eduardo José", "Velasco Ramírez", "evelasco@gmail.com", "Finanzas y Contabilidad",
                    new UserEntity("EVelasco", passwordEncoder.encode("eduardo"), "evelasco@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0024", "Natalia Sofía", "Cortez Aguirre", "ncortez@gmail.com", "Derecho Civil",
                    new UserEntity("NCortez", passwordEncoder.encode("natalia"), "ncortez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0025", "Roberto Antonio", "Zelaya Cruz", "rzelaya@gmail.com", "Arquitectura Sostenible",
                    new UserEntity("RZelaya", passwordEncoder.encode("roberto"), "rzelaya@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0026", "Melissa Daniela", "Flores Rivera", "mflores@gmail.com", "Ingeniería en Redes",
                    new UserEntity("MFlores", passwordEncoder.encode("melissa"), "mflores@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0027", "Ernesto Miguel", "Reyes Vásquez", "ereyes@gmail.com", "Automatización de Procesos",
                    new UserEntity("EReyes", passwordEncoder.encode("ernesto"), "ereyes@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0028", "Clara Eugenia", "Domínguez Chávez", "cdominguez@gmail.com", "Inteligencia de Negocios",
                    new UserEntity("CDominguez", passwordEncoder.encode("clara"), "cdominguez@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0029", "Héctor Manuel", "Castillo Pérez", "hcastillo@gmail.com", "Programación Orientada a Objetos",
                    new UserEntity("HCastillo", passwordEncoder.encode("hector"), "hcastillo@gmail.com", UserState.ACTIVE, rolTeacher)
            ));

            teacherRepository.save(new TeacherEntity(
                    "T0030", "Beatriz Elena", "Serrano López", "bserrano@gmail.com", "Administración de Proyectos de Software",
                    new UserEntity("BSerrano", passwordEncoder.encode("beatriz"), "bserrano@gmail.com", UserState.ACTIVE, rolTeacher)
            ));



            logger.info("✅ Teacher Created Successfully::::Mysql");
            logger.info("📊 Total teacher: " + teacherRepository.count());
        } else {
            logger.info("ℹ️ DB already contains teachers. Total teacher: " + teacherRepository.count());
        }

        /*========================
            -----Periods------
         ========================
        * */

        if (periodRepository.count() == 0) {
            logger.info("🔄 Creating  Periods...");

            periodRepository.save(new PeriodEntity("Ciclo I", 2026, LocalDate.of(2026, 01, 20), LocalDate.of(2026, 06, 06), "Diurno"));
            periodRepository.save(new PeriodEntity("Interciclo", 2026, LocalDate.of(2026, 06, 15), LocalDate.of(2026, 07, 18), "Intensivo"));
            periodRepository.save(new PeriodEntity("Ciclo II", 2026, LocalDate.of(2026, 01, 28), LocalDate.of(2026, 12, 12), "Diurno"));

            logger.info("✅ Periods Created Successfully::::Mysql");
            logger.info("📊 Total Periods: " + periodRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Periods. Total Periods: " + periodRepository.count());
        }

        /*========================
            -----Schedules------
         ========================
        * */

        if (scheduleRepository.count() == 0) {
            logger.info("🔄 Creating  Schedules...");

            // --- HORARIOS ENTRE SEMANA (2h por día) ---
            scheduleRepository.save(new ScheduleEntity("Lunes_Miércoles", "06:30-08:30"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Miércoles", "08:40-10:40"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Miércoles", "10:50-12:50"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Miércoles", "13:00-15:00"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Miércoles", "15:10-17:10"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Miércoles", "17:20-19:20"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Miércoles", "19:30-21:30"));

            scheduleRepository.save(new ScheduleEntity("Lunes_Viernes", "06:30-08:30"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Viernes", "08:40-10:40"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Viernes", "10:50-12:50"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Viernes", "13:00-15:00"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Viernes", "15:10-17:10"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Viernes", "17:20-19:20"));
            scheduleRepository.save(new ScheduleEntity("Lunes_Viernes", "19:30-21:30"));

            scheduleRepository.save(new ScheduleEntity("Martes_Jueves", "06:30-08:30"));
            scheduleRepository.save(new ScheduleEntity("Martes_Jueves", "08:40-10:40"));
            scheduleRepository.save(new ScheduleEntity("Martes_Jueves", "10:50-12:50"));
            scheduleRepository.save(new ScheduleEntity("Martes_Jueves", "13:00-15:00"));
            scheduleRepository.save(new ScheduleEntity("Martes_Jueves", "15:10-17:10"));
            scheduleRepository.save(new ScheduleEntity("Martes_Jueves", "17:20-19:20"));
            scheduleRepository.save(new ScheduleEntity("Martes_Jueves", "19:30-21:30"));

            scheduleRepository.save(new ScheduleEntity("Miércoles_Viernes", "06:30-08:30"));
            scheduleRepository.save(new ScheduleEntity("Miércoles_Viernes", "08:40-10:40"));
            scheduleRepository.save(new ScheduleEntity("Miércoles_Viernes", "10:50-12:50"));
            scheduleRepository.save(new ScheduleEntity("Miércoles_Viernes", "13:00-15:00"));
            scheduleRepository.save(new ScheduleEntity("Miércoles_Viernes", "15:10-17:10"));
            scheduleRepository.save(new ScheduleEntity("Miércoles_Viernes", "17:20-19:20"));
            scheduleRepository.save(new ScheduleEntity("Miércoles_Viernes", "19:30-21:30"));

            // --- SÁBADO (4h) ---
            scheduleRepository.save(new ScheduleEntity("Sábado", "07:00-11:00"));
            scheduleRepository.save(new ScheduleEntity("Sábado", "11:10-15:10"));
            scheduleRepository.save(new ScheduleEntity("Sábado", "15:20-19:20"));

            // --- DOMINGO (4h) ---
            scheduleRepository.save(new ScheduleEntity("Domingo", "07:00-11:00"));
            scheduleRepository.save(new ScheduleEntity("Domingo", "11:10-15:10"));

            logger.info("✅ Schedules Created Successfully::::Mysql");
            logger.info("📊 Total Schedules: " + scheduleRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Schedules. Total Schedules: " + scheduleRepository.count());
        }

        /*========================
            -----Classroom------
         ========================
        * */

        if (classroomRepository.count() == 0) {
            logger.info("🔄 Creating  classroom...");

            // AULA VIRTUAL
            classroomRepository.save(new ClassroomEntity("EN LÍNEA", 150, "Teams"));

            // INGENIERÍA
            classroomRepository.save(new ClassroomEntity("ING-101", 30, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-102", 30, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-201", 35, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-LAB REDES", 25, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-LAB ELECTRÓNICA", 20, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-202", 32, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-203", 34, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-301", 38, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-302", 40, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-303", 36, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-LAB PROGRAMACIÓN", 28, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-LAB MECÁNICA", 22, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-LAB AUTOMATIZACIÓN", 26, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-AUDITORIO", 90, "Ingeniería"));
            classroomRepository.save(new ClassroomEntity("ING-SALA MULTIMEDIA", 45, "Ingeniería"));


            // FRANCISCO MORAZÁN
            classroomRepository.save(new ClassroomEntity("FM-101", 40, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-202", 38, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-303", 42, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-405", 40, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-LAB 1", 32, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-LAB 2", 28, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-102", 38, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-204", 36, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-304", 42, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-406", 40, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-502", 38, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-LAB 3", 30, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-LAB 4", 28, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-LAB COMPUTACIÓN", 35, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-SALA ACADÉMICA", 50, "Francisco Morazán"));
            classroomRepository.save(new ClassroomEntity("FM-AUDITORIO 2", 120, "Francisco Morazán"));


            // ECONOMÍA
            classroomRepository.save(new ClassroomEntity("ECO-101", 45, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-202", 40, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-303", 35, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-102", 40, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-203", 36, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-304", 35, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-305", 37, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-401", 42, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-LAB FINANZAS", 28, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-LAB EMPRENDIMIENTO", 30, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-SALA DE CONFERENCIAS", 60, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-TALLER DE NEGOCIOS", 35, "Economía"));
            classroomRepository.save(new ClassroomEntity("ECO-AUDITORIO", 100, "Economía"));


            // DERECHO
            classroomRepository.save(new ClassroomEntity("DJ-201", 50, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-301", 40, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-101", 45, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-102", 40, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-202", 48, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-302", 38, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-401", 50, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-AULA MAGNA", 95, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-SALA DE SIMULACIÓN", 30, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-SALA DE JUICIOS", 35, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-LAB INVESTIGACIÓN", 28, "Derecho"));
            classroomRepository.save(new ClassroomEntity("DJ-AUDITORIO", 120, "Derecho"));


            // ARTES
            classroomRepository.save(new ClassroomEntity("ART-101", 25, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-201", 25, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-LAB DISEÑO", 20, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-102", 28, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-202", 26, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-301", 30, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-302", 32, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-303", 25, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-LAB ILUSTRACIÓN", 20, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-LAB FOTOGRAFÍA", 18, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-TALLER DE PINTURA", 22, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-TALLER DE ESCULTURA", 20, "Artes"));
            classroomRepository.save(new ClassroomEntity("ART-AUDITORIO", 80, "Artes"));


            // HUMANIDADES
            classroomRepository.save(new ClassroomEntity("HUM-101", 35, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-102", 35, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-201", 40, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-103", 35, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-202", 38, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-203", 40, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-301", 45, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-302", 42, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-LAB PSICOLOGÍA", 28, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-LAB EDUCACIÓN", 30, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-SALA DE LECTURA", 25, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-SALA MULTIMEDIA", 35, "Humanidades"));
            classroomRepository.save(new ClassroomEntity("HUM-AUDITORIO", 90, "Humanidades"));


        // CAMPUS CENTRAL
            classroomRepository.save(new ClassroomEntity("AUDITORIO CENTRAL", 150, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("AUD-PEQUEÑO", 70, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("AUD-ANEXO 1", 80, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("AUD-ANEXO 2", 85, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("CENTRO-CONF 1", 60, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("CENTRO-CONF 2", 55, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("CENTRO-CONF 3", 70, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("LAB-CENTRAL 1", 30, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("LAB-CENTRAL 2", 32, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("LAB-CENTRAL 3", 28, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("CENTRAL-MULTIMEDIA", 50, "Campus Central"));
            classroomRepository.save(new ClassroomEntity("CENTRAL-AUDITORIO 3", 140, "Campus Central"));


            logger.info("✅ classroom Created Successfully::::Mysql");
            logger.info("📊 Total classroom: " + classroomRepository.count());
        } else {
            logger.info("ℹ️ DB already contains classroom. Total classroom: " + classroomRepository.count());
        }

        /*========================
        -----Subject Assigments------
         ========================
        * */

        if (subjectAssignedRepository.count() == 0) {
            logger.info("🔄 Creating  subjectAssigned...");

                    // -------------------------------------------------------------
        // 1. Programación I – Sección A – Ciclo I
        // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(1L).get(),
                            teacherRepository.findById(1L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(1L).get(),
                            classroomRepository.findById(1L).get(),
                            30, 30, "A"
                    )
            );

            // -------------------------------------------------------------
            // 2. Matemática I – Sección B – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(2L).get(),
                            teacherRepository.findById(2L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(2L).get(),
                            classroomRepository.findById(2L).get(),
                            35, 35, "B"
                    )
            );

            // -------------------------------------------------------------
            // 3. Introducción a Sistemas – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(3L).get(),
                            teacherRepository.findById(3L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(3L).get(),
                            classroomRepository.findById(3L).get(),
                            40, 40, "A"
                    )
            );

            // -------------------------------------------------------------
            // 4. Redes I – Sección C – Interciclo
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(4L).get(),
                            teacherRepository.findById(4L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(4L).get(),
                            classroomRepository.findById(4L).get(),
                            25, 25, "C"
                    )
            );

            // -------------------------------------------------------------
            // 5. Inglés Técnico (Virtual) – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(5L).get(),
                            teacherRepository.findById(5L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(5L).get(),
                            classroomRepository.findById(5L).get(), // EN LÍNEA
                            50, 50, "A"
                    )
            );

            // -------------------------------------------------------------
            // 6. Base de Datos I – Sección B – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(6L).get(),
                            teacherRepository.findById(6L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(6L).get(),
                            classroomRepository.findById(6L).get(),
                            30, 30, "B"
                    )
            );

            // -------------------------------------------------------------
            // 7. Estadística I – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(7L).get(),
                            teacherRepository.findById(7L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(7L).get(),
                            classroomRepository.findById(7L).get(),
                            28, 28, "A"
                    )
            );

            // -------------------------------------------------------------
            // 8. Programación II – Sección B – ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(8L).get(),
                            teacherRepository.findById(8L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(8L).get(),
                            classroomRepository.findById(8L).get(),
                            32, 32, "B"
                    )
            );

            // -------------------------------------------------------------
            // 9. Ética Profesional – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(9L).get(),
                            teacherRepository.findById(9L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(9L).get(),
                            classroomRepository.findById(9L).get(),
                            45, 45, "A"
                    )
            );

            // -------------------------------------------------------------
            // 10. Metodología de la Investigación – Sección C – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(10L).get(),
                            teacherRepository.findById(10L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(10L).get(),
                            classroomRepository.findById(10L).get(),
                            20, 20, "C"
                    )
            );

            // -------------------------------------------------------------
            // 11. Administración de Proyectos – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(11L).get(),
                            teacherRepository.findById(3L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(11L).get(),
                            classroomRepository.findById(11L).get(),
                            35, 35, "A"
                    )
            );

            // -------------------------------------------------------------
            // 12. Redes II – Sección B – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(12L).get(),
                            teacherRepository.findById(4L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(12L).get(),
                            classroomRepository.findById(12L).get(),
                            30, 30, "B"
                    )
            );

            // -------------------------------------------------------------
            // 13. Seguridad Informática – Sección A – ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(13L).get(),
                            teacherRepository.findById(7L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(13L).get(),
                            classroomRepository.findById(13L).get(),
                            25, 25, "A"
                    )
            );

            // -------------------------------------------------------------
            // 14. Programación Web – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(14L).get(),
                            teacherRepository.findById(1L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(14L).get(),
                            classroomRepository.findById(14L).get(),
                            40, 40, "A"
                    )
            );

            // -------------------------------------------------------------
            // 15. Arquitectura de Computadoras – Sección B – ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(15L).get(),
                            teacherRepository.findById(8L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(15L).get(),
                            classroomRepository.findById(15L).get(),
                            30, 30, "B"
                    )
            );

            // -------------------------------------------------------------
            // 16. Análisis de Sistemas – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(16L).get(),
                            teacherRepository.findById(6L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(16L).get(),
                            classroomRepository.findById(16L).get(),
                            50, 50, "A"
                    )
            );

            // -------------------------------------------------------------
            // 17. Inteligencia Artificial – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(17L).get(),
                            teacherRepository.findById(9L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(17L).get(),
                            classroomRepository.findById(17L).get(),
                            45, 45, "A"
                    )
            );

            // -------------------------------------------------------------
            // 18. Auditoría Informática – Sección C – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(18L).get(),
                            teacherRepository.findById(10L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(18L).get(),
                            classroomRepository.findById(18L).get(),
                            20, 20, "C"
                    )
            );

            // -------------------------------------------------------------
            // 19. Matemática Discreta – Sección B – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(19L).get(),
                            teacherRepository.findById(2L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(19L).get(),
                            classroomRepository.findById(19L).get(),
                            35, 35, "B"
                    )
            );

            // -------------------------------------------------------------
            // 20. Desarrollo Móvil – Sección A – Ciclo I
            // -------------------------------------------------------------
            subjectAssignedRepository.save(
                    new SubjectAssignedEntity(
                            subjectRepository.findById(20L).get(),
                            teacherRepository.findById(5L).get(),
                            periodRepository.findById(1L).get(),
                            scheduleRepository.findById(20L).get(),
                            classroomRepository.findById(20L).get(),
                            30, 30, "A"
                    )
            );


            logger.info("✅ subjectAssigned Created Successfully::::Mysql");
            logger.info("📊 Total subjectAssigned: " + subjectAssignedRepository.count());
        } else {
            logger.info("ℹ️ DB already contains subjectAssigned. Total subjectAssigned: " + subjectAssignedRepository.count());
        }

    }
}
