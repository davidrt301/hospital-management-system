package com.davidrt301.medicare.model;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
/**
 * Mapea la clase a la tabla 'persons'.
 * uniqueConstraints: Define una restricción de unicidad para la columna 'email',
 * impidiendo que existan correos duplicados en el sistema.
 */
@Table(name = "persons", uniqueConstraints = @jakarta.persistence.UniqueConstraint(columnNames = "email"))
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

}
