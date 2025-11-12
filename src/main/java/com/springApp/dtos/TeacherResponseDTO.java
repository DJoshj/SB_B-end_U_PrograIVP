package com.springApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO {
    private Long teacherId;
    private Long userId;
    private String teacherCode;
    private String names;
    private String lastName;
    private String email;
    private String speciality;
}
