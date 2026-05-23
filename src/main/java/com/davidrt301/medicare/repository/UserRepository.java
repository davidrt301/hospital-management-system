package com.davidrt301.medicare.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.User;

/**
 * Repositorio para la entidad User.
 * @Repository se encarga de la traducción de excepciones de persistencia
 * y permite que Spring gestione esta interfaz como un bean de acceso a datos.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUserName(String userName);

    Optional<User> findByPerson(Person person);

    boolean existsByUserName(String userName);

    @Query("SELECT u FROM User u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :user, '%'))")
    Page<User> searchByUser(@Param("user") String user, Pageable pageable);
}
