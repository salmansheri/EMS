package com.company.ems.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateAttendanceRequest(
        UUID employeeId,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        UUID shiftId) {

}
