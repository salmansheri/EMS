package com.company.ems.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.Shift;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {
    boolean existsByShiftName(String name);
}
