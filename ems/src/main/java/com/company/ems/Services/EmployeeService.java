package com.company.ems.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.company.ems.DTOs.CreateEmployeeRequestDto;
import com.company.ems.DTOs.EmployeeDto;
import com.company.ems.Mappers.EmployeeMapper;
import com.company.ems.Repositories.DepartmentRepository;
import com.company.ems.Repositories.DesignationRepository;
import com.company.ems.Repositories.EmployeeRepository;
import com.company.ems.models.Employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository; 
    private final DepartmentRepository departmentRepository; 
    private final DesignationRepository designationRepository; 
    private final EmployeeMapper employeeMapper;
    
    @Cacheable(value = "ems::employees")
    public List<EmployeeDto> getAllEmployees() {
        log.info("Loading employees from DB");
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList(); 
    }

    @Cacheable(value="ems::employee", key = "#id")
    public EmployeeDto getEmployeeById(UUID id) {
        log.info("Loading employee from DB", id);
        if (id == null) {
            throw new IllegalArgumentException("Employee Id Cannot be null"); 
        }
        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));  

        return employeeMapper.toDto(emp); 
    }

    @CacheEvict(value={"ems::employees"}, allEntries = true)
    public EmployeeDto createEmployee(CreateEmployeeRequestDto request) {
        Employee employee = employeeMapper.toEntity(request);
        
        if (employee == null) {
            throw new IllegalArgumentException("request payload cannot be null"); 
        }

        employeeRepository.save(employee); 

        return employeeMapper.toDto(employee); 
    }

    @SuppressWarnings("null")
    @CachePut(value="ems::employee", key="#id")
    @CacheEvict(value="ems::employees", allEntries = true)
    public EmployeeDto updaEmployee(UUID id, CreateEmployeeRequestDto request) {
        if (id == null || request == null)  {
            throw new IllegalArgumentException("id or request cannot be null"); 
        }

        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found")); 

          emp.setFirstName(request.firstName());
        emp.setLastName(request.lastName());
        emp.setEmail(request.email());
        emp.setPhone(request.phone());
        emp.setDateOfBirth(request.dateOfBirth());
        emp.setDateOfJoining(request.dateOfJoining());
        emp.setGender(request.gender());

         emp.setDepartment(departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found")));

        emp.setDesignation(designationRepository.findById(request.designationId())
                .orElseThrow(() -> new EntityNotFoundException("Designation not found")));

        employeeRepository.save(emp); 

        return employeeMapper.toDto(emp); 
    }

    @Caching (evict = {
        @CacheEvict(value="ems::employee", key="#id"),
        @CacheEvict(value="ems::employees", allEntries = true)

    })
    public void deleteEmployee(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null"); 
        }
        employeeRepository.deleteById(id);
    }
    
}
