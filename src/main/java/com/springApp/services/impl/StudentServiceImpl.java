package com.springApp.services.impl;

import com.springApp.dtos.StudentDTO;
import com.springApp.dtos.StudentResponseDTO;
import com.springApp.entity.CareerEntity;
import com.springApp.entity.RolEntity;
import com.springApp.entity.StudentEntity;
import com.springApp.entity.UserEntity;
import com.springApp.entity.states.UserState;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.StudentMapper;
import com.springApp.repositories.CareerRepository;
import com.springApp.repositories.RolRepository;
import com.springApp.repositories.StudentRepository;
import com.springApp.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final RolRepository rolRepository;
    private final CareerRepository careerRepository;
    private final PasswordEncoder passwordEncoder;

    // =======================================
    // GENERAR CÓDIGO DE FACULTAD (PERSISTENTE)
    // =======================================
    private String generateFacultyCodeForFaculty(String faculty) {

        String normalized = faculty.trim().toLowerCase();

        // 1. Verificar si alguna carrera con esa facultad YA tiene código
        List<CareerEntity> careers = careerRepository.findByFacultyIgnoreCase(faculty);

        for (CareerEntity c : careers) {
            if (c.getFacultyCode() != null) {
                return c.getFacultyCode(); // ✔ Código existente → reutilizar
            }
        }

        // 2. No existe código → generar uno nuevo
        int maxCode = careerRepository.findMaxFacultyCode()
                .orElse(15);

        int newCode = maxCode + 2;

        // 3. Asignar el código a todas las carreras de esa facultad
        for (CareerEntity c : careers) {
            c.setFacultyCode(String.valueOf(newCode));
        }


        careerRepository.saveAll(careers);

        log.info("");
        return String.valueOf(newCode);
    }


    // =======================================
    // GENERAR CARNET DEL ESTUDIANTE
    // =======================================
    private String generateCarnet(CareerEntity career) {

        // Obtener código basado en la FACULTAD, no en la carrera
        String facultyCode = generateFacultyCodeForFaculty(career.getFaculty());

        int year = LocalDate.now().getYear();

        long correlativo = studentRepository.count() + 1;
        String correlativoStr = String.format("%04d", correlativo);

        return facultyCode + "-" + correlativoStr + "-" + year;
    }


    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentDTO dto) {
        log.info("Creando estudiante: {}", dto.getName());

        // Obtener carrera
        CareerEntity career = null;
        if (dto.getCareerId() != null) {
            career = careerRepository.findById(dto.getCareerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        }

        // ASIGNAR CÓDIGO DE FACULTAD SI ES NECESARIO
        String facultyCode = generateFacultyCodeForFaculty(career.getFaculty());

        // ACTUALIZAR LA CARRERA (solo si está vacía)
        if (career.getFacultyCode() == null || !career.getFacultyCode().equals(facultyCode)) {
            career.setFacultyCode(facultyCode);
            careerRepository.save(career);
        }

        // GENERAR AUTOMÁTICAMENTE EL CARNET
        String carnetGenerado = generateCarnet(career);
        dto.setCarnet(carnetGenerado);


        // Validar duplicados
        if (studentRepository.existsByCarnet(dto.getCarnet())) {
            throw new DuplicateResourceException("Ya existe un estudiante con el carnet: " + dto.getCarnet());
        }

        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un estudiante con el email: " + dto.getEmail());
        }

        // Crear el usuario desde el DTO
        UserEntity user= new UserEntity();
        user.setUsername(dto.getCarnet().replace("-", "")); //username sera el nombre del carnet del estudiante
        user.setPassword(passwordEncoder.encode(dto.getUser().getPassword()));
        user.setEmail(dto.getEmail());
        user.setState(UserState.ACTIVE);
        RolEntity rol = rolRepository.findById(3L)  //rol student
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        user.setRoles(rol);

        // Crear la entidad Student
        StudentEntity student = new StudentEntity();
        student.setUser(user);
        student.setCarnet(dto.getCarnet());
        student.setName(dto.getName());
        student.setLastname(dto.getLastname());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setCareer(career);

        StudentEntity saved = studentRepository.save(student);
        log.info("Estudiante creado con ID: {}", saved.getStudentId());

        return studentMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentDTO dto) {
        log.info("Actualizando estudiante ID: {}", id);

        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

        // Validar duplicados si cambió el carnet
        if (!student.getCarnet().equals(dto.getCarnet()) &&
                studentRepository.existsByCarnet(dto.getCarnet())) {
            throw new DuplicateResourceException("Ya existe un estudiante con el carnet: " + dto.getCarnet());
        }

        // Actualizar campos
        student.setCarnet(dto.getCarnet());
        student.setName(dto.getName());
        student.setLastname(dto.getLastname());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());

        if (dto.getCareerId() != null) {
            CareerEntity career = careerRepository.findById(dto.getCareerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
            student.setCareer(career);
        }

        StudentEntity updated = studentRepository.save(student);
        log.info("Estudiante actualizado: {}", id);

        return studentMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
        return studentMapper.toDTO(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByCarnet(String carnet) {
        StudentEntity student = studentRepository.findByCarnet(carnet)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con carnet: " + carnet));
        return studentMapper.toDTO(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponseDTO> getStudentsByCareer(Long careerId) {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estudiante no encontrado");
        }
        studentRepository.deleteById(id);
        log.info("Estudiante eliminado: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countStudentsByCareer(Long careerId) {
        return studentRepository.countByCareer(careerId);
    }
}
