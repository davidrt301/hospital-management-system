package com.davidrt301.medicare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.davidrt301.medicare.model.Specialty;
import com.davidrt301.medicare.model.Status;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    
    Page<Specialty> findByStatus(Status estado, Pageable pageable);

    @Query("SELECT e FROM Specialty e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Specialty> searchByName(@Param("name") String name, Pageable pageable);

}
