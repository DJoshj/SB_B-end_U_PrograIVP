package com.springApp.repositories;

import com.springApp.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    Optional<SubjectEntity> findBySubjectCode(String subjectCode);

    List<SubjectEntity> findByCareerCareerId(Long careerId);

    boolean existsBySubjectCode(String subjectCode);

    @Query("SELECT s FROM SubjectEntity s WHERE s.valueUnits = :units")
    List<SubjectEntity> findByValueUnits(@Param("units") Integer units);

    @Query("SELECT COUNT(s) FROM SubjectEntity s WHERE s.career.careerId = :careerId")
    Long countByCareer(@Param("careerId") Long careerId);
}
