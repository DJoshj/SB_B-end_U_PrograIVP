package com.springApp.repositories;

import com.springApp.entity.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodEntity, Long> {
    List<PeriodEntity> findByYear(Integer year);

    Optional<PeriodEntity> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT p FROM PeriodEntity p WHERE " +
            "CURRENT_DATE BETWEEN p.startDate AND p.endDate")
    Optional<PeriodEntity> findCurrentPeriod();

    @Query("SELECT p FROM PeriodEntity p WHERE p.year = :year ORDER BY p.startDate DESC")
    List<PeriodEntity> findByYearOrderByStartDateDesc(@Param("year") Integer year);

    @Query("SELECT p FROM PeriodEntity p ORDER BY p.year DESC, p.startDate DESC")
    List<PeriodEntity> findAllOrderedByYearAndDate();

    @Query("SELECT DISTINCT p.year FROM PeriodEntity p ORDER BY p.year DESC")
    List<Integer> findAllYears();
}
