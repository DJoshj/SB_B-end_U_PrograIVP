package com.springApp.repositories;

import com.springApp.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByCarnet(String carnet);

    Optional<StudentEntity> findByUserUserId(Long userId);

    List<StudentEntity> findByCareerCareerId(Long careerId);

    boolean existsByCarnet(String carnet);

    boolean existsByEmail(String email);

    @Query("SELECT s FROM StudentEntity s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.lastname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.carnet) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<StudentEntity> searchStudents(@Param("search") String search);

    @Query("SELECT COUNT(s) FROM StudentEntity s WHERE s.career.careerId = :careerId")
    Long countByCareer(@Param("careerId") Long careerId);
}
