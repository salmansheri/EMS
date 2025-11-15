package com.company.ems.DTOs;

import java.time.LocalTime;
import java.util.UUID;

public record ShiftDto(
        UUID id,
        String shiftName,
        LocalTime startTime,
        LocalTime endTime) {

}
