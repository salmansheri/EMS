package com.company.ems.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.AttendanceLog;

public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, UUID> {
     List<AttendanceLog> findByEmployeeId(UUID employeeId);

    List<AttendanceLog> findByDate(LocalDate date);

    // List<AttendanceLog> findByEmployeeIdAndDate(UUID employeeId, LocalDate date);

    Optional<AttendanceLog> findFirstByEmployeeIdAndDate(UUID employeeId, LocalDate date); 
    Optional<AttendanceLog> findByEmployeeIdAndDate(UUID employeeId, LocalDate date);


    boolean existsByEmployeeIdAndDate(UUID employeeId, LocalDate date);
    
}
