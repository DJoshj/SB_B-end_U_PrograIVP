package com.springApp.repositories;

import com.springApp.entity.CareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity, Long> {
    List<CareerEntity> findByFacultyIgnoreCase(String faculty);

    @Query("SELECT MAX(CAST(c.facultyCode AS integer)) FROM CareerEntity c")
    Optional<Integer> findMaxFacultyCode();

    Optional<CareerEntity> findByNameCareer(String nameCareer);

    List<CareerEntity> findByFaculty(String faculty);

    boolean existsByNameCareer(String nameCareer);

    @Query("SELECT c FROM CareerEntity c WHERE " +
            "LOWER(c.nameCareer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.faculty) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<CareerEntity> searchCareers(@Param("search") String search);

    @Query("SELECT DISTINCT c.faculty FROM CareerEntity c WHERE c.faculty IS NOT NULL")
    List<String> findAllFaculties();
}
