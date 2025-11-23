package com.springApp.mapper;

import com.springApp.dtos.SubjectAssignedResponseDTO;
import com.springApp.entity.SubjectAssignedEntity;
import org.springframework.stereotype.Component;

@Component
public class SubjectAssignedResponseMapper {
    // ENTIDAD → DTO
    public SubjectAssignedResponseDTO toDTO(SubjectAssignedEntity entity) {

        if (entity == null) return null;

        return SubjectAssignedResponseDTO.builder()
                .subjectAssignedId(entity.getIdSubjectAssigned())

                .subjectId(entity.getSubject().getSubjectId())
                .subjectCode(entity.getSubject().getSubjectCode())
                .subjectName(entity.getSubject().getName())

                .teacherId(entity.getTeacher().getTeacherId())
                .teacherName(entity.getTeacher().getNames() + " " + entity.getTeacher().getLastName())
                .teacherCode(entity.getTeacher().getTeacherCode())

                .periodId(entity.getPeriod().getPeriodId())
                .periodName(entity.getPeriod().getName())

                .scheduleId(entity.getSchedule().getScheduleId())
                .scheduleDays(entity.getSchedule().getDays())
                .scheduleTime(entity.getSchedule().getSchedule())

                .classroomId(entity.getClassroom().getClassroomId())
                .classroomName(entity.getClassroom().getName())
                .building(entity.getClassroom().getBuilding())

                .section(entity.getSection())
                .availableSpace(entity.getAvailableSpace())
                .maximumCapacity(entity.getMaximumCapacity())

                // CALCULADO EN EL MOMENTO
                .enrolledStudents(entity.getMaximumCapacity() - entity.getAvailableSpace())

                .build();
    }

}
