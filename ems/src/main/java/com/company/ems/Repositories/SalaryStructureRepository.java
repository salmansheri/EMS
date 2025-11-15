package com.company.ems.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.SalaryStructure;

public interface SalaryStructureRepository extends JpaRepository<SalaryStructure, UUID> {

    Optional<SalaryStructure> findByEmployeeId(UUID employeeId);

    boolean existsByEmployeeId(UUID employeeId);

}
