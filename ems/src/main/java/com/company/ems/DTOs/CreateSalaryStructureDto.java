package com.company.ems.DTOs;

import java.util.UUID;

import com.company.ems.models.Employee;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public record CreateSalaryStructureDto(
    UUID id,

     double basicPay,
     double hra,
     double allowances,
     double deductions,

     boolean active,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
     Employee employee
) {
    
}
