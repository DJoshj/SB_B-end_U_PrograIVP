package com.springApp.services.impl;

import com.springApp.dtos.CareerDTO;
import com.springApp.dtos.CareerResponseDTO;
import com.springApp.entity.CareerEntity;
import com.springApp.exception.DuplicateResourceException;
import com.springApp.exception.ResourceNotFoundException;
import com.springApp.mapper.CareerMapper;
import com.springApp.repositories.CareerRepository;
import com.springApp.services.CareerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {
    private final CareerMapper careerMapper;
    private final CareerRepository careerRepository;

    @Override
    @Transactional
    public CareerResponseDTO createCareer(CareerDTO dto) {
        if (careerRepository.existsByNameCareer(dto.getNameCareer())) {
            throw new DuplicateResourceException("Ya existe una carrera con ese nombre");
        }

        CareerEntity career = new CareerEntity();
        career.setNameCareer(dto.getNameCareer());
        career.setFaculty(dto.getFaculty());

        CareerEntity saved = careerRepository.save(career);

        return careerMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public CareerResponseDTO updateCareer(Long id, CareerDTO dto) {
        CareerEntity career = careerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));

        career.setNameCareer(dto.getNameCareer());
        career.setFaculty(dto.getFaculty());

        CareerEntity saved = careerRepository.save(career);

        return careerMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CareerResponseDTO getCareerById(Long id) {
        CareerEntity career = careerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        return careerMapper.toDTO(career);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CareerResponseDTO> getAllCareers() {
        return careerRepository.findAll().stream()
                .map(careerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CareerResponseDTO> getCareersByFaculty(String faculty) {
        return careerRepository.findByFaculty(faculty).stream()
                .map(careerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CareerResponseDTO> searchCareers(String search) {
        return careerRepository.searchCareers(search).stream()
                .map(careerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllFaculties() {
        return careerRepository.findAllFaculties();
    }

    @Override
    @Transactional
    public void deleteCareer(Long id) {
        if (!careerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Carrera no encontrada");
        }
        careerRepository.deleteById(id);
    }
}
