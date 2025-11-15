package com.springApp.repositories;

import com.springApp.entity.InscriptionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionsEntity, Long> {
    // ========== BÚSQUEDAS BÁSICAS ==========
    List<InscriptionsEntity> findBySubjectAssignedIdSubjectAssigned(Long subjectAssignedId);
    List<InscriptionsEntity> findByPeriodPeriodId(Long periodId);
    // Obtener inscripciones por estudiante
    List<InscriptionsEntity> findByStudentStudentId(Long studentId);

    // ========== VALIDACIONES ==========
    // Verificar si ya está inscrito
    @Query("SELECT COUNT(i) > 0 FROM InscriptionsEntity i WHERE " +
            "i.student.studentId = :studentId AND " +
            "i.subjectAssigned.idSubjectAssigned = :subjectAssignedId AND " +
            "i.state = 'ACTIVE'")
    boolean existsActiveInscription(
            @Param("studentId") Long studentId,
            @Param("subjectAssignedId") Long subjectAssignedId
    );

    // Verificar si el estudiante ya aprobó la materia
    @Query("SELECT COUNT(i) > 0 FROM InscriptionsEntity i WHERE " +
            "i.student.studentId = :studentId AND " +
            "i.subjectAssigned.subject.subjectId = :subjectId AND " +
            "i.state = 'COMPLETED'")
    boolean hasCompletedSubject(
            @Param("studentId") Long studentId,
            @Param("subjectId") Long subjectId
    );

    // ========== BÚSQUEDAS COMBINADAS ==========
    // Obtener inscripciones por periodo
    @Query("SELECT i FROM InscriptionsEntity i WHERE " +
            "i.student.studentId = :studentId AND " +
            "i.period.periodId = :periodId")
    List<InscriptionsEntity> findByStudentAndPeriod(
            @Param("studentId") Long studentId,
            @Param("periodId") Long periodId
    );

    @Query("SELECT i FROM InscriptionsEntity i WHERE " +
            "i.subjectAssigned.teacher.teacherId = :teacherId AND " +
            "i.period.periodId = :periodId")
    List<InscriptionsEntity> findByTeacherAndPeriod(
            @Param("teacherId") Long teacherId,
            @Param("periodId") Long periodId
    );

    // ========== CONTEOS Y ESTADÍSTICAS ==========

    // Contar inscripciones activas por materia asignada
    @Query("SELECT COUNT(i) FROM InscriptionsEntity i WHERE " +
            "i.subjectAssigned.idSubjectAssigned = :subjectAssignedId AND " +
            "i.state = 'ACTIVE'")
    Long countActiveInscriptionsBySubjectAssigned(@Param("subjectAssignedId") Long subjectAssignedId);

    @Query("SELECT COUNT(i) FROM InscriptionsEntity i WHERE " +
            "i.student.studentId = :studentId AND " +
            "i.period.periodId = :periodId AND " +
            "i.state = 'ACTIVE'")
    Long countActiveByStudentAndPeriod(
            @Param("studentId") Long studentId,
            @Param("periodId") Long periodId
    );

    @Query("SELECT COUNT(i) FROM InscriptionsEntity i WHERE " +
            "i.student.studentId = :studentId AND " +
            "i.state = 'COMPLETED'")
    Long countCompletedByStudent(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(i) FROM InscriptionsEntity i WHERE " +
            "i.period.periodId = :periodId AND " +
            "i.state = 'ACTIVE'")
    Long countActiveByPeriod(@Param("periodId") Long periodId);

    // ========== BÚSQUEDAS POR FECHA ==========

    @Query("SELECT i FROM InscriptionsEntity i WHERE " +
            "i.inscriptionDate BETWEEN :startDate AND :endDate")
    List<InscriptionsEntity> findByDateRange(
            @Param("startDate") java.time.LocalDate startDate,
            @Param("endDate") java.time.LocalDate endDate
    );

    // ========== REPORTES ==========

    @Query("SELECT i FROM InscriptionsEntity i " +
            "JOIN FETCH i.student s " +
            "JOIN FETCH i.subjectAssigned sa " +
            "JOIN FETCH sa.subject sub " +
            "JOIN FETCH sa.teacher t " +
            "WHERE i.period.periodId = :periodId")
    List<InscriptionsEntity> findAllWithDetailsByPeriod(@Param("periodId") Long periodId);



}
