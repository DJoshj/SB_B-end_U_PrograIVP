package com.springApp.services;

import com.springApp.dtos.SubjectDTO;
import com.springApp.dtos.SubjectResponseDTO;

import java.util.List;

public interface SubjectService {
    SubjectResponseDTO createSubject(SubjectDTO dto);
    SubjectResponseDTO updateSubject(Long id, SubjectDTO dto);
    SubjectResponseDTO getSubjectById(Long id);
    SubjectResponseDTO getSubjectByCode(String subjectCode);
    List<SubjectResponseDTO> getAllSubjects();
    List<SubjectResponseDTO> getSubjectsByCareer(Long careerId);
    List<SubjectResponseDTO> searchSubjects(String search);
    void deleteSubject(Long id);
}
