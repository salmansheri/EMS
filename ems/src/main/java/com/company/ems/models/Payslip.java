package com.company.ems.models;

import java.time.LocalDate;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payslips")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payslip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate month;
    private double grossSalary;
    private double netSalary;
    private double taxAmount;

    private String pdfUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
