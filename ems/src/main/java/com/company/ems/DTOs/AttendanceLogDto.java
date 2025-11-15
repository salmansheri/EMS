package com.company.ems.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AttendanceLogDto(
    UUID id,
        LocalDate date,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        double totalHours,
        UUID employeeId,
        String employeeName,
        UUID shiftId,
        String shiftName
) {
    
}
