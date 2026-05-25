package com.davidrt301.medicare.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.Status;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    Optional<Person> findByEmail(String email);

    Page<Person> findByStatus(Status status, Pageable pageable);

    /**
     * Realiza una búsqueda de personas por nombre utilizando una coincidencia parcial.
     * La búsqueda es insensible a mayúsculas y minúsculas (case-insensitive).
     * 
     * @param name El fragmento de nombre o nombre completo a buscar.
     * @param pageable La configuración de paginación (página, tamaño, orden).
     * @return Una página de resultados con las personas que coinciden con el criterio.
     */
    @Query("SELECT p FROM Person p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Person> searchByName(@Param("name") String name, Pageable pageable);
}
