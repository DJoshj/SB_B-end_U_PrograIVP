package com.springApp.services;

import com.springApp.dtos.CareerDTO;
import com.springApp.dtos.CareerResponseDTO;

import java.util.List;

public interface CareerService {
    CareerResponseDTO createCareer(CareerDTO dto);
    CareerResponseDTO updateCareer(Long id, CareerDTO dto);
    CareerResponseDTO getCareerById(Long id);
    List<CareerResponseDTO> getAllCareers();
    List<CareerResponseDTO> getCareersByFaculty(String faculty);
    List<CareerResponseDTO> searchCareers(String search);
    List<String> getAllFaculties();
    void deleteCareer(Long id);
}
