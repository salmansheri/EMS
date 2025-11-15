package com.company.ems.DTOs;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeDto(
     UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate dateOfJoining,
        LocalDate dateOfBirth,
        String gender,
        String departmentName,
        String designationTitle,
        String status
) {
    
}
