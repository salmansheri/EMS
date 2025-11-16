package com.company.ems.DTOs;

import java.time.LocalDate;
import java.util.UUID;

public record PayslipDto(
        UUID id,
        LocalDate month,
        double grossSalary,
        double netSalary,
        double taxAmount,
        String pdfUrl,
        UUID employeeId) {

}
