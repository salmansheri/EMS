package com.company.ems.Schedular;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.company.ems.Repositories.AttendanceLogRepository;
import com.company.ems.Repositories.EmployeeRepository;
import com.company.ems.models.AttendanceLog;
import com.company.ems.models.enums.AttendanceStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AttendanceSchedular {
    private final EmployeeRepository employeeRepository; 
    private final AttendanceLogRepository attendanceLogRepository; 


    // Run every midnight + 1 minute
    @Scheduled(cron = "0 1 0 * * *")
    public void createMissingAttendance() {
        LocalDate yesterday = LocalDate.now().minusDays(1); 

        employeeRepository.findAll().forEach(employee -> {
            boolean exists = attendanceLogRepository.existsByEmployeeIdAndDate(employee.getId(), yesterday); 

            if (!exists) {
                AttendanceLog attendanceLog = AttendanceLog.builder()
                    .employee(employee)
                    .date(yesterday)
                    .checkIn(null)
                    .checkOut(null)
                    .totalHours(0)
                    .status(AttendanceStatus.ABSENT)
                    .build(); 

                attendanceLogRepository.save(attendanceLog); 

                     log.info("Created ABSENT attendance for {}", employee.getId());
            }
        });
    }
    
}
