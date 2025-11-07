package com.springApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {
    private Long studentId;
    private Long userId;
    private String username;
    private String carnet;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private Long careerId;
    private String careerName;
}
