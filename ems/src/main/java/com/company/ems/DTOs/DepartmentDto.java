package com.company.ems.DTOs;

import java.util.UUID;

public record DepartmentDto(
        UUID id,
        String name,
        String description) {

}
