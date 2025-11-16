package com.company.ems.DTOs;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDto(
    UUID id,
        String title,
        String description,
        String status,
        LocalDate assignedDate,
        LocalDate acceptedDate,
        LocalDate completedDate,
        UUID assignedById,
        UUID assignedToId
) {
} 
