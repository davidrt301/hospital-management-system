package com.davidrt301.medicare.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.davidrt301.medicare.model.Attention;
import com.davidrt301.medicare.model.Employee;
import com.davidrt301.medicare.model.Patient;
import com.davidrt301.medicare.model.Status;

@Repository
public interface AttentionRepository extends JpaRepository<Attention, Long> {
    

    Page<Attention> findByPatient(Patient patient, Pageable pageable);

    Page<Attention> findByEmployee(Employee employee, Pageable pageable);

    Page<Attention> findByStatus(Status status, Pageable pageable);

    Page<Attention> findByDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    /**
     * Busca Attentiones cuya descripción contenga el texto proporcionado (sin distinguir mayúsculas).
     * 
     * @param description fragmento de texto a buscar
     * @param pageable configuración de paginación
     * @return página de Attentiones encontradas
     */
    @Query("SELECT a FROM Attention a WHERE LOWER(a.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    Page<Attention> searchByDescription(@Param("description") String description, Pageable pageable);
}
