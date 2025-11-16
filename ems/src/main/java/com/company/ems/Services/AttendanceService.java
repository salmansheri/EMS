package com.company.ems.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.company.ems.DTOs.AttendanceLogDto;
import com.company.ems.DTOs.CreateAttendanceRequest;
import com.company.ems.Mappers.AttendanceMapper;
import com.company.ems.Repositories.AttendanceLogRepository;
import com.company.ems.Repositories.EmployeeRepository;
import com.company.ems.Repositories.ShiftRepository;
import com.company.ems.models.AttendanceLog;
import com.company.ems.models.enums.AttendanceStatus;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceService {
    private final AttendanceLogRepository attendanceLogRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final AttendanceMapper attendanceMapper;

    @Cacheable(value = "ems::attendanceByEmp", key = "#employeeId")
    public List<AttendanceLogDto> getAttendanceByEmployee(UUID employeeId) {
        log.info("Fetching Attendances for employee from DB");
        return attendanceLogRepository.findByEmployeeId(employeeId)
                .stream()
                .map(attendanceMapper::toDto)
                .toList();
    }

    @Cacheable(value = "ems::attendanceByEmpAndToday", key = "#employeeId")
    public AttendanceLogDto getTodayAttendance(UUID employeeId) {
          log.info("Fetching Today Attendance for employee from DB");
        LocalDate today = LocalDate.now();

        AttendanceLog attendanceLog = attendanceLogRepository.findFirstByEmployeeIdAndDate(employeeId, today)
                .orElseThrow(() -> new EntityNotFoundException("No attendance Found"));

        return attendanceMapper.toDto(attendanceLog);
    }

    @Caching(evict =  {
        @CacheEvict(value = "attendanceByEmp", key = "#employeeId"),
        @CacheEvict(value = "attendanceByDate", key = "T(java.time.LocalDate).now()"),
        @CacheEvict(value = "attendanceByEmpAndToday", key = "#employeeId")


    })
    public AttendanceLogDto markAttendance(UUID employeeId) {
        LocalDate today = LocalDate.now(); 
        LocalDateTime now = LocalDateTime.now();

        Optional<AttendanceLog> existingAttendance = attendanceLogRepository.findByEmployeeIdAndDate(employeeId, today); 

        AttendanceLog attendanceLog; 

        if (existingAttendance.isEmpty()) {
            attendanceLog = AttendanceLog.builder()
                .employee(employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found")))
                .date(today)
                .checkIn(now)
                .build();

            attendanceLogRepository.save(attendanceLog); 
            return attendanceMapper.toDto(attendanceLog); 
        }

        attendanceLog = existingAttendance.get(); 
        if (attendanceLog.getCheckIn() != null && attendanceLog.getCheckOut() == null) {
            attendanceLog.setCheckOut(now);

             double hours = (double)
                (now.toLocalTime().toSecondOfDay() -
                 attendanceLog.getCheckIn().toLocalTime().toSecondOfDay()) / 3600;

            attendanceLog.setTotalHours(hours);

            // Status Logic
            if (hours >= 9) {
                attendanceLog.setStatus(AttendanceStatus.PRESENT);
            } else if (hours >= 4) {
                attendanceLog.setStatus(AttendanceStatus.HALF_DAY);
            } else {
                attendanceLog.setStatus(AttendanceStatus.ABSENT);
            }

            attendanceLogRepository.save(attendanceLog); 

            return attendanceMapper.toDto(attendanceLog); 
        }

        throw new IllegalStateException("Attendance already Completed for today"); 
    }

    @Cacheable(value = "ems::attendanceByDate", key = "#date")
    public List<AttendanceLogDto> getAttendanceByDate(LocalDate date) {
        return attendanceLogRepository.findByDate(date).stream().map(attendanceMapper::toDto).toList();
    }

    @Caching(evict = {
            @CacheEvict(value = "ems::attendanceByEmp", key = "#req.employeeId"),
            @CacheEvict(value = "ems::attendanceByDate", key = "#req.employeeId")

    })
    public AttendanceLogDto logAttendance(CreateAttendanceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }

        AttendanceLog attendanceLog = AttendanceLog.builder()
                .employee(employeeRepository.findById(request.employeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found")))
                .checkIn(request.checkIn())
                .checkOut(request.checkOut())
                .date(request.checkIn().toLocalDate())
                .shift(shiftRepository.findById(request.shiftId())
                        .orElseThrow(() -> new EntityNotFoundException("Shirt not found")))
                .build();

        attendanceLog.setTotalHours(
                (double) (request.checkOut().toLocalTime().toSecondOfDay() -
                        request.checkIn().toLocalTime().toSecondOfDay()) / 3600);

        attendanceLogRepository.save(attendanceLog);

        return attendanceMapper.toDto(attendanceLog);

    }

}
