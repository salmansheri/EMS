package com.company.ems.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.company.ems.DTOs.PayslipDto;
import com.company.ems.DTOs.SalaryStructureDto;
import com.company.ems.Mappers.PayslipMapper;
import com.company.ems.Mappers.SalaryStructureMapper;
import com.company.ems.Repositories.PayslipRepository;
import com.company.ems.Repositories.SalaryStructureRepository;
import com.company.ems.models.Payslip;
import com.company.ems.models.SalaryStructure;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollServiceService {
    private final SalaryStructureRepository salaryStructureRepository;
    private final PayslipRepository payslipRepository;
    private final SalaryStructureMapper salaryStructureMapper;
    private final PayslipMapper payslipMapper;

    @Cacheable(value = "ems::salaryStructure", key = "#employeeId")
    public SalaryStructureDto getSalaryStructure(UUID employeeId) {
        SalaryStructure salaryStructure = salaryStructureRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Salary Structure not found"));

        return salaryStructureMapper.toDto(salaryStructure);
    }

    @CachePut(value = "ems:salaryStructure", key = "#employeeId")
    @CacheEvict(value = "ems::payslips", key = "#employeeId")
    public SalaryStructureDto updateSalary(UUID employeeId, SalaryStructure salaryStructure) {
        salaryStructure.setEmployee(salaryStructure.getEmployee());
        salaryStructureRepository.save(salaryStructure);

        return salaryStructureMapper.toDto(salaryStructure);

    }

    @Cacheable(value = "ems::payslips", key = "#employeeId")
    public List<PayslipDto> getPayslip(UUID employeeId) {
        return payslipRepository.findByEmployeeId(employeeId).stream().map(payslipMapper::toDto).toList();
    }

    @Cacheable(value = "ems::payslip", key = "#id")
    public PayslipDto getPayslipById(UUID id) {
        Payslip payslip = payslipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payslip not found"));

        return payslipMapper.toDto(payslip);
    }

    @Caching(evict = {
            @CacheEvict(value = "payslips", key = "#employeeId"),
            @CacheEvict(value = "payslip", allEntries = true)
    })
    public PayslipDto generatePayslip(UUID employeeId, Payslip payslip) {
        payslip.setEmployee(payslip.getEmployee());
        payslipRepository.save(payslip);

        return payslipMapper.toDto(payslip);
    }

}
