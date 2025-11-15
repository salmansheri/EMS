package com.company.ems.DTOs;

import java.time.LocalDate;
import java.util.UUID;

public record LeaveRequestDto(
     UUID id,
        LocalDate startDate,
        LocalDate endDate,
        String type,
        String status,
        String reason,
        UUID employeeId
) {
    
}
