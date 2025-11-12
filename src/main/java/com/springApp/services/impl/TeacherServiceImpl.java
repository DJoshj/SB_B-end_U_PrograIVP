package com.springApp.services.impl;

import com.springApp.dtos.TeacherDTO;
import com.springApp.dtos.TeacherResponseDTO;
import com.springApp.entity.RolEntity;
import com.springApp.entity.TeacherEntity;
import com.springApp.entity.UserEntity;
import com.springApp.entity.states.UserState;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.TeacherMapper;
import com.springApp.repositories.RolRepository;
import com.springApp.repositories.TeacherRepository;
import com.springApp.services.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TeacherResponseDTO createTeacher(TeacherDTO dto) {
        log.info("Creando docente: {}", dto.getTeacherCode());

        if (teacherRepository.existsByTeacherCode(dto.getTeacherCode())) {
            throw new DuplicateResourceException("Ya existe un docente con el código: " + dto.getTeacherCode());
        }

        if (teacherRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un docente con el email: " + dto.getEmail());
        }

        // Crear el usuario desde el DTO
        UserEntity user= new UserEntity();
        user.setUsername(dto.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(dto.getUser().getPassword()));
        user.setEmail(dto.getEmail());
        user.setState(UserState.ACTIVE);
        RolEntity rol = rolRepository.findById(dto.getUser().getRoles().getRolId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        user.setRoles(rol);

        TeacherEntity teacher = new TeacherEntity();
        teacher.setUser(user);
        teacher.setTeacherCode(dto.getTeacherCode());
        teacher.setNames(dto.getNames());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());
        teacher.setSpeciality(dto.getSpeciality());

        TeacherEntity saved = teacherRepository.save(teacher);
        log.info("Teacher created with ID: {}", saved.getTeacherId());

        return teacherMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public TeacherResponseDTO updateTeacher(Long id, TeacherDTO dto) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado"));

        teacher.setTeacherCode(dto.getTeacherCode());
        teacher.setNames(dto.getNames());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());
        teacher.setSpeciality(dto.getSpeciality());

        TeacherEntity teacherUpdated = teacherRepository.save(teacher);
        log.info("Teacher Updated with ID: {}", teacher.getTeacherId());
        return teacherMapper.toDTO(teacherUpdated);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDTO getTeacherById(Long id) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado"));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDTO getTeacherByCode(String teacherCode) {
        TeacherEntity teacher = teacherRepository.findByTeacherCode(teacherCode)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado"));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> getTeachersBySpecialty(String specialty) {
        return teacherRepository.findBySpeciality(specialty).stream()
                .map(teacherMapper::toDTO)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> searchTeachers(String search) {
        return teacherRepository.searchTeachers(search).stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllSpecialties() {
        return teacherRepository.findAllSpecialties();
    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Docente no encontrado");
        }
        log.info("Teacher Deleted with ID: {}", id);
        teacherRepository.deleteById(id);
    }
}
