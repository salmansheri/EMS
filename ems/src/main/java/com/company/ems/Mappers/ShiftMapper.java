package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.ShiftDto;
import com.company.ems.models.Shift;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShiftMapper {

    ShiftDto toDto(Shift shift);
    Shift toEntity(ShiftDto dto);
    
}
