package com.company.ems.DTOs;

import java.time.LocalDate;
import java.util.UUID;

public record CreateEmployeeRequestDto(
     String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate dateOfJoining,
        LocalDate dateOfBirth,
        String gender,
        UUID departmentId,
        UUID designationId
) {
    
}
