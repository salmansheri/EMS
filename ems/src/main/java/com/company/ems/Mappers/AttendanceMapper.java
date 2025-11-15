package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.AttendanceLogDto;
import com.company.ems.models.AttendanceLog;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper {
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.firstName", target = "employeeName")
    @Mapping(source = "shift.id", target = "shiftId")
    @Mapping(source = "shift.shiftName", target = "shiftName")
    AttendanceLogDto toDto(AttendanceLog log);

}
