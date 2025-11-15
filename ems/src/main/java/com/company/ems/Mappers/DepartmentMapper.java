package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.DepartmentDto;
import com.company.ems.models.Department;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface DepartmentMapper {
      DepartmentDto toDto(Department department);

    Department toEntity(DepartmentDto dto);
    
}
