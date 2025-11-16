package com.company.ems.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.company.ems.DTOs.AssignTaskRequestDto;
import com.company.ems.DTOs.TaskDto;
import com.company.ems.Mappers.TaskMapper;
import com.company.ems.Repositories.EmployeeRepository;
import com.company.ems.Repositories.TaskRepository;
import com.company.ems.models.Employee;
import com.company.ems.models.Task;
import com.company.ems.models.enums.TaskStatus;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskMapper taskMapper;

    @Cacheable(value = "ems::tasksByEmployee", key = "#employeeId")
    public List<TaskDto> getTasksForEmployee(UUID employeeId) {
        return taskRepository.findByAssignedToId(employeeId).stream().map(taskMapper::toDto).toList();

    }

    @Cacheable(value = "ems::tasksByEmployee", key = "#request.assignedTo")
    public TaskDto assignTask(AssignTaskRequestDto request) {

        Employee assignedBy = employeeRepository.findById(request.assignedBy())
                .orElseThrow(() -> new EntityNotFoundException("Assigned By not found"));

        Employee assignedTo = employeeRepository.findById(request.assignedTo())
                .orElseThrow(() -> new EntityNotFoundException("Assigned to not found"));

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .assignedBy(assignedBy)
                .assignedTo(assignedTo)
                .assignedDate(LocalDate.now())
                .status(TaskStatus.ASSIGNED)
                .dueDate(request.dueDate())
                .build();

        taskRepository.save(task);

        return taskMapper.toDto(task);

    }

    @CacheEvict(value = "tasksByEmployee", key = "#employeeId")
    public TaskDto acceptTask(UUID taskId, UUID employeeId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!task.getAssignedTo().getId().equals(employeeId)) {
            throw new IllegalStateException("You are not allowed to accept this task");
        }

        task.setStatus(TaskStatus.ACCEPTED);
        task.setAccepDate(LocalDate.now());

        taskRepository.save(task);

        return taskMapper.toDto(task);
    }

    @CacheEvict(value = "tasksByEmployee", key = "#employeeId")
    public TaskDto completeTask(UUID taskId, UUID employeeId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!task.getAssignedTo().getId().equals(employeeId)) {
            throw new IllegalStateException("You cannot Complete a task not assigned to you");
        }

        if (task.getStatus() != TaskStatus.ACCEPTED) {
            throw new IllegalStateException("Task must be accepted before completion");

        }

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedDate(LocalDate.now());

        taskRepository.save(task);

        return taskMapper.toDto(task);
    }

}
