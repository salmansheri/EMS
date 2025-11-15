package com.company.ems.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    boolean existsByName(String name);

}
