package com.company.ems.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.company.ems.DTOs.TaskDto;
import com.company.ems.models.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(source = "assignedBy.id", target = "assignedById")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    TaskDto toDto(Task task);
}