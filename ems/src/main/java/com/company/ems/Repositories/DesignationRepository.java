package com.company.ems.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ems.models.Designation;

public interface DesignationRepository extends JpaRepository<Designation, UUID> {
      boolean existsByTitle(String title);
    
}
