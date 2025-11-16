package com.company.ems.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.company.ems.models.enums.AttendanceStatus;

import jakarta.persistence.*;
import lombok.*; 

@Entity
@Table(name = "attendance_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceLog {
      @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate date; 
    private LocalDateTime checkIn; 
    private LocalDateTime checkOut; 

    private double totalHours; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee; 

     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; 
    
}
