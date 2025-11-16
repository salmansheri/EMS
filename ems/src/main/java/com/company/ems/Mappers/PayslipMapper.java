package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.PayslipDto;

import com.company.ems.models.Payslip;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayslipMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    PayslipDto toDto(Payslip payslip);

}
