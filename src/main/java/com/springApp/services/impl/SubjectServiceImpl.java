package com.springApp.services.impl;

import com.springApp.dtos.SubjectDTO;
import com.springApp.dtos.SubjectResponseDTO;
import com.springApp.entity.CareerEntity;
import com.springApp.entity.SubjectEntity;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.SubjectMapper;
import com.springApp.repositories.CareerRepository;
import com.springApp.repositories.SubjectRepository;
import com.springApp.services.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final CareerRepository careerRepository;

    @Override
    @Transactional
    public SubjectResponseDTO createSubject(SubjectDTO dto) {
        if (subjectRepository.existsBySubjectCode(dto.getSubjectCode())) {
            throw new DuplicateResourceException("Ya existe una materia con el código: " + dto.getSubjectCode());
        }

        CareerEntity career = careerRepository.findById(dto.getCareerId())
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));

        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectCode(dto.getSubjectCode());
        subject.setName(dto.getName());
        subject.setValueUnits(dto.getValueUnits());
        subject.setCareer(career);

        return subjectMapper.toDTO(subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public SubjectResponseDTO updateSubject(Long id, SubjectDTO dto) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));

        subject.setSubjectCode(dto.getSubjectCode());
        subject.setName(dto.getName());
        subject.setValueUnits(dto.getValueUnits());

        if (dto.getCareerId() != null) {
            CareerEntity career = careerRepository.findById(dto.getCareerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
            subject.setCareer(career);
        }

        return subjectMapper.toDTO(subjectRepository.save(subject));
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getSubjectById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));
        return subjectMapper.toDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getSubjectByCode(String subjectCode) {
        SubjectEntity subject = subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));
        return subjectMapper.toDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getSubjectsByCareer(Long careerId) {
        return subjectRepository.findByCareerCareerId(careerId).stream()
                .map(subjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materia no encontrada");
        }
        subjectRepository.deleteById(id);
    }
}
