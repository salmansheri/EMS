package com.company.ems.Mappers;

import com.company.ems.DTOs.DesignationDto;
import com.company.ems.models.Designation;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DesignationMapper {
    DesignationDto toDto(Designation designation);

    Designation toEntity(DesignationDto dto);

}
