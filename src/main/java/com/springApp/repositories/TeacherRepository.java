package com.springApp.repositories;

import com.springApp.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    Optional<TeacherEntity> findByTeacherCode(String teacherCode);

    Optional<TeacherEntity> findByUserUserId(Long userId);

    boolean existsByTeacherCode(String teacherCode);

    boolean existsByEmail(String email);

    List<TeacherEntity> findBySpecialty(String specialty);

    @Query("SELECT t FROM TeacherEntity t WHERE " +
            "LOWER(t.names) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(t.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(t.teacherCode) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<TeacherEntity> searchTeachers(@Param("search") String search);

    @Query("SELECT DISTINCT t.specialty FROM TeacherEntity t WHERE t.specialty IS NOT NULL")
    List<String> findAllSpecialties();
}
