package com.company.ems.models;

import java.time.LocalDate;

import java.util.UUID;

import com.company.ems.models.enums.LeaveStatus;
import com.company.ems.models.enums.LeaveType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveType type;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
