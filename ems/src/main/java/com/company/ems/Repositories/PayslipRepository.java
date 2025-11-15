package com.company.ems.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.Payslip;

public interface PayslipRepository extends JpaRepository<Payslip, UUID> {
    List<Payslip> findByEmployeeId(UUID employeeId);

    List<Payslip> findByMonth(LocalDate month);

    boolean existsByEmployeeIdAndMonth(UUID employeeId, LocalDate month);

}
