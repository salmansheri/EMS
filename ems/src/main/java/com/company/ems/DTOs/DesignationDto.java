package com.company.ems.DTOs;

import java.util.UUID;

public record DesignationDto(
        UUID id,
        String title,
        String description) {

}
