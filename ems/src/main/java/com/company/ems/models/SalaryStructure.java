package com.company.ems.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salary_structures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double basicPay;
    private double hra;
    private double allowances;
    private double deductions;

    private boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
