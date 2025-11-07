package com.springApp.services.impl;

import com.springApp.dtos.StudentDTO;
import com.springApp.dtos.StudentResponseDTO;
import com.springApp.entity.CareerEntity;
import com.springApp.entity.StudentEntity;
import com.springApp.entity.UserEntity;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.StudentMapper;
import com.springApp.repositories.CareerRepository;
import com.springApp.repositories.StudentRepository;
import com.springApp.repositories.UserRepository;
import com.springApp.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CareerRepository careerRepository;

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentDTO dto) {
        log.info("Creando estudiante: {}", dto.getCarnet());

        // Validar duplicados
        if (studentRepository.existsByCarnet(dto.getCarnet())) {
            throw new DuplicateResourceException("Ya existe un estudiante con el carnet: " + dto.getCarnet());
        }

        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un estudiante con el email: " + dto.getEmail());
        }

        // Obtener usuario
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Obtener carrera si se proporciona
        CareerEntity career = null;
        if (dto.getCareerId() != null) {
            career = careerRepository.findById(dto.getCareerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        }

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
        return List.of();
    }

    @Override
    public List<StudentResponseDTO> getStudentsByCareer(Long careerId) {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> searchStudents(String search) {
        return studentRepository.searchStudents(search).stream()
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
