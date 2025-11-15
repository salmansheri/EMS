package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.LeaveRequestDto;
import com.company.ems.models.LeaveRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveRequestMapper {
     @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "status", target = "status")
    LeaveRequestDto toDto(LeaveRequest leave);
    
}
