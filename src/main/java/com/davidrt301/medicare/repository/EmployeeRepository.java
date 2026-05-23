package com.davidrt301.medicare.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.davidrt301.medicare.model.Employee;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.Status;

/**
 * Repositorio de Spring Data JPA para la entidad Employee.
 * Proporciona métodos CRUD, paginación y consultas derivadas automáticamente.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Recupera empleados filtrados por estado con soporte para paginación.
     * 
     * @param status Estado del empleado (ACTIVO, INACTIVO)
     * @param pageable Configuración de paginación
     * @return Página de empleados encontrados
     */
    Page<Employee> findByStatus(Status status, Pageable pageable);

    /**
     * Busca un empleado vinculado a una persona específica.
     * 
     * @param person Entidad persona asociada
     * @return Optional con el empleado si existe
     */
    Optional<Employee> findByPerson(Person person);
}
