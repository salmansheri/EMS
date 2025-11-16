package com.company.ems.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.Task;
import com.company.ems.models.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByAssignedToId(UUID employeeId);

    List<Task> findByAssignedById(UUID employeeId);

    List<Task> findByStatus(TaskStatus status);

}
