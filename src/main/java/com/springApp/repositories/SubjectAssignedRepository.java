package com.springApp.repositories;

import com.springApp.entity.SubjectAssignedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectAssignedRepository extends JpaRepository<SubjectAssignedEntity, Long> {
    // ========== BÚSQUEDAS BÁSICAS ==========

    List<SubjectAssignedEntity> findByClassroomClassroomId(Long classroomId);
    List<SubjectAssignedEntity> findByScheduleScheduleId(Long scheduleId);
    List<SubjectAssignedEntity> findBySubjectSubjectId(Long subjectId);
    // Obtener materias asignadas por periodo
    List<SubjectAssignedEntity> findByPeriodPeriodId(Long periodId);


    // Obtener materias por docente
    List<SubjectAssignedEntity> findByTeacherTeacherId(Long teacherId);

    // ========== VALIDACIONES DE CONFLICTOS ==========

    @Query("""
    SELECT sa 
    FROM SubjectAssignedEntity sa
    JOIN FETCH sa.subject
    JOIN FETCH sa.teacher
    JOIN FETCH sa.period
    JOIN FETCH sa.schedule
    JOIN FETCH sa.classroom
    """)
    List<SubjectAssignedEntity> findAllWithRelations();

    // Verificar si existe una asignación duplicada
    @Query("SELECT COUNT(sa) > 0 FROM SubjectAssignedEntity sa WHERE " +
            "sa.subject.subjectId = :subjectId AND " +
            "sa.teacher.teacherId = :teacherId AND " +
            "sa.period.periodId = :periodId AND " +
            "sa.section = :section")
    boolean existsBySubjectAndTeacherAndPeriodAndSection(
            @Param("subjectId") Long subjectId,
            @Param("teacherId") Long teacherId,
            @Param("periodId") Long periodId,
            @Param("section") String section
    );

    // Verificar conflicto de aula
    @Query("SELECT COUNT(sa) > 0 FROM SubjectAssignedEntity sa WHERE " +
            "sa.classroom.classroomId = :classroomId AND " +
            "sa.period.periodId = :periodId AND " +
            "sa.schedule.scheduleId = :scheduleId")
    boolean existsClassroomScheduleConflict(
            @Param("classroomId") Long classroomId,
            @Param("periodId") Long periodId,
            @Param("scheduleId") Long scheduleId
    );

    // Verificar conflicto de horario para el docente
    @Query("SELECT COUNT(sa) > 0 FROM SubjectAssignedEntity sa WHERE " +
            "sa.teacher.teacherId = :teacherId AND " +
            "sa.period.periodId = :periodId AND " +
            "sa.schedule.scheduleId = :scheduleId")
    boolean existsTeacherScheduleConflict(
            @Param("teacherId") Long teacherId,
            @Param("periodId") Long periodId,
            @Param("scheduleId") Long scheduleId
    );

    // ========== BÚSQUEDAS AVANZADAS ==========
    // Obtener materias asignadas con cupo disponible
    @Query("SELECT sa FROM SubjectAssignedEntity sa WHERE " +
            "sa.availableSpace > 0 AND sa.period.periodId = :periodId")
    List<SubjectAssignedEntity> findAvailableByPeriod(@Param("periodId") Long periodId);

    @Query("SELECT sa FROM SubjectAssignedEntity sa WHERE " +
            "sa.teacher.teacherId = :teacherId AND " +
            "sa.period.periodId = :periodId")
    List<SubjectAssignedEntity> findByTeacherAndPeriod(
            @Param("teacherId") Long teacherId,
            @Param("periodId") Long periodId
    );

    @Query("SELECT sa FROM SubjectAssignedEntity sa WHERE " +
            "sa.subject.career.careerId = :careerId AND " +
            "sa.period.periodId = :periodId")
    List<SubjectAssignedEntity> findByCareerAndPeriod(
            @Param("careerId") Long careerId,
            @Param("periodId") Long periodId
    );

    @Query("SELECT sa FROM SubjectAssignedEntity sa WHERE " +
            "sa.subject.career.careerId = :careerId AND " +
            "sa.period.periodId = :periodId AND " +
            "sa.availableSpace > 0")
    List<SubjectAssignedEntity> findAvailableByCareerAndPeriod(
            @Param("careerId") Long careerId,
            @Param("periodId") Long periodId
    );

    // ========== ESTADÍSTICAS ==========
    @Query("SELECT COUNT(sa) FROM SubjectAssignedEntity sa WHERE sa.period.periodId = :periodId")
    Long countByPeriod(@Param("periodId") Long periodId);

    @Query("SELECT COUNT(sa) FROM SubjectAssignedEntity sa WHERE sa.teacher.teacherId = :teacherId")
    Long countByTeacher(@Param("teacherId") Long teacherId);

    @Query("SELECT SUM(sa.maximumCapacity - sa.availableSpace) FROM SubjectAssignedEntity sa " +
            "WHERE sa.period.periodId = :periodId")
    Long getTotalEnrolledByPeriod(@Param("periodId") Long periodId);

    @Query("SELECT sa FROM SubjectAssignedEntity sa WHERE sa.availableSpace = 0")
    List<SubjectAssignedEntity> findFullSubjects();





}
