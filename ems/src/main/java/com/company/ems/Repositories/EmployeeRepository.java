package com.company.ems.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.Employee;
import com.company.ems.models.enums.EmploymentStatus;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByDepartmentId(UUID departmentId);

    List<Employee> findByDesignationId(UUID designationId);

    List<Employee> findByStatus(EmploymentStatus status);

    boolean existsByEmail(String email);

    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

}
