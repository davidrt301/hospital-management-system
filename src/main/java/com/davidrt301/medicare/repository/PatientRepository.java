package com.davidrt301.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.davidrt301.medicare.model.Patient;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.Status;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    List<Patient> findByStatus(Status status);

    Page<Patient> findByStatus(Status status, Pageable pageable);

    boolean existsByPerson(Person person);

    @Query("SELECT p FROM Patient p JOIN User u ON u.person = p.person WHERE u.userName = :username")
    Optional<Patient> findByUserUsername(@Param("username") String username);
}
