package com.springApp.repositories;

import com.springApp.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
    List<ClassroomEntity> findByBuilding(String building);

    Optional<ClassroomEntity> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT c FROM ClassroomEntity c WHERE c.ability >= :minCapacity")
    List<ClassroomEntity> findByMinimumCapacity(@Param("minCapacity") Integer minCapacity);

    @Query("SELECT c FROM ClassroomEntity c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.building) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<ClassroomEntity> searchClassrooms(@Param("search") String search);

    @Query("SELECT DISTINCT c.building FROM ClassroomEntity c WHERE c.building IS NOT NULL ORDER BY c.building")
    List<String> findAllDistinctBuildings();

    @Query("SELECT c FROM ClassroomEntity c WHERE c.building = :building ORDER BY c.name")
    List<ClassroomEntity> findByBuildingOrderByName(@Param("building") String building);
}
