package com.company.ems.DTOs;

import java.util.UUID;

public record SalaryStructureDto(
        UUID id,
        double basicPay,
        double hra,
        double allowances,
        double deductions,
        boolean active,
        UUID employeeId) {

}
