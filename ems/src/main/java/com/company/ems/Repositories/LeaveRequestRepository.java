package com.company.ems.Repositories;

import java.util.List;
import java.util.UUID;

import com.company.ems.models.LeaveRequest;
import com.company.ems.models.enums.LeaveStatus;

public interface LeaveRequestRepository {
    List<LeaveRequest> findByEmployeeId(UUID employeeId);

    List<LeaveRequest> findByStatus(LeaveStatus status);

    List<LeaveRequest> findByEmployeeIdAndStatus(UUID employeeId, LeaveStatus status);
}
