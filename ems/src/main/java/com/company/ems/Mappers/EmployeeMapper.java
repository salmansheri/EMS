package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.CreateEmployeeRequestDto;
import com.company.ems.DTOs.EmployeeDto;
import com.company.ems.models.Employee;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface EmployeeMapper {
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "department.title", target = "designationTitle")
    EmployeeDto toDto(Employee employee);
    
    @Mapping(source = "departmentId", target = "department.id")
    @Mapping(source = "designationId", target = "designation.id")
    Employee toEntity(CreateEmployeeRequestDto dto); 
    
}
