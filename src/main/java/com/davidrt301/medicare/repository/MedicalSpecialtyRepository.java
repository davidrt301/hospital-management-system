package com.davidrt301.medicare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidrt301.medicare.model.Employee;
import com.davidrt301.medicare.model.MedicalSpecialty;
import com.davidrt301.medicare.model.Specialty;

@Repository
public interface MedicalSpecialtyRepository extends JpaRepository<MedicalSpecialty, Long> {
    
    
    Page<MedicalSpecialty> findByEmployee(Employee employee, Pageable pageable);

    Page<MedicalSpecialty> findBySpecialty(Specialty specialty, Pageable pageable);
}
