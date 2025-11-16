package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.CreateEmployeeRequestDto;
import com.company.ems.DTOs.SalaryStructureDto;
import com.company.ems.models.Employee;
import com.company.ems.models.SalaryStructure;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SalaryStructureMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    SalaryStructureDto toDto(SalaryStructure structure); 

    //  @Mapping(source = "departmentId", target = "department.id")
    // @Mapping(source = "designationId", target = "designation.id")
    // Employee toEntity(CreateEmployeeRequestDto dto); 
    
}
