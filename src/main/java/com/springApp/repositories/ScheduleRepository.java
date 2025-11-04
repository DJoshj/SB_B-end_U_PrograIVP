package com.springApp.repositories;

import com.springApp.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByDaysContaining(String day);

    @Query("SELECT s FROM ScheduleEntity s WHERE " +
            "LOWER(s.days) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.schedule) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<ScheduleEntity> searchSchedules(@Param("search") String search);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.days = :days AND s.schedule = :time")
    Optional<ScheduleEntity> findByDaysAndTime(
            @Param("days") String days,
            @Param("time") String time
    );

    @Query("SELECT DISTINCT s.days FROM ScheduleEntity s ORDER BY s.days")
    List<String> findAllDistinctDays();
}
