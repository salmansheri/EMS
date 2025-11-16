package com.company.ems.DTOs;

import java.time.LocalDate;
import java.util.UUID;

public record AssignTaskRequestDto(
        String title,
        String description,
        UUID assignedBy,
        UUID assignedTo,
        LocalDate dueDate) {

}
