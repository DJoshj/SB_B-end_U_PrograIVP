package com.springApp.services.impl;

import com.springApp.dtos.ClassroomDTO;
import com.springApp.dtos.ClassroomResponseDTO;
import com.springApp.entity.ClassroomEntity;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.ClassroomMapper;
import com.springApp.repositories.ClassroomRepository;
import com.springApp.services.ClassroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomMapper classroomMapper;
    private final ClassroomRepository classroomRepository;

    @Override
    @Transactional
    public ClassroomResponseDTO createClassroom(ClassroomDTO dto) {
        if (classroomRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Ya existe un aula con ese nombre");
        }

        ClassroomEntity classroom = new ClassroomEntity();
        classroom.setName(dto.getName());
        classroom.setAbility(dto.getAbility());
        classroom.setBuilding(dto.getBuilding());

        return classroomMapper.toDTO(classroomRepository.save(classroom));
    }

    @Override
    @Transactional
    public ClassroomResponseDTO updateClassroom(Long id, ClassroomDTO dto) {
        ClassroomEntity classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aula no encontrada"));

        classroom.setName(dto.getName());
        classroom.setAbility(dto.getAbility());
        classroom.setBuilding(dto.getBuilding());

        return classroomMapper.toDTO(classroomRepository.save(classroom));
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomResponseDTO getClassroomById(Long id) {
        ClassroomEntity classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aula no encontrada"));
        return classroomMapper.toDTO(classroom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDTO> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(classroomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDTO> getClassroomsByBuilding(String building) {
        return classroomRepository.findByBuilding(building).stream()
                .map(classroomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDTO> getClassroomsByMinCapacity(Integer minCapacity) {
        return classroomRepository.findByMinimumCapacity(minCapacity).stream()
                .map(classroomMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> getAllBuildings() {
        return classroomRepository.findAllDistinctBuildings();
    }

    @Override
    @Transactional
    public void deleteClassroom(Long id) {
        if (!classroomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aula no encontrada");
        }
        classroomRepository.deleteById(id);
    }
}
