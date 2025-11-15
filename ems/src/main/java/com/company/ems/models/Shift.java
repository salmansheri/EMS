package com.company.ems.models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shifts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String shiftName;

    private LocalTime startTime;
    private LocalTime endTime;

    @OneToMany(mappedBy = "shift")
    private List<AttendanceLog> attendanceLogs = new ArrayList<>();

}
