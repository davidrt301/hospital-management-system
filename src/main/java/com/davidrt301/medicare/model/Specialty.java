package com.davidrt301.medicare.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "specialties")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Relación uno a muchos con MedicalSpecialty.
     * - mappedBy: La relación es gobernada por el campo 'specialty' en la entidad MedicalSpecialty.
     * - cascade: Las operaciones de persistencia se propagan automáticamente a los hijos.
     * - orphanRemoval: Elimina automáticamente de la BD las especialidades médicas que sean
     *   removidas de este Set.
     */
    @OneToMany(mappedBy = "specialty", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<MedicalSpecialty> employees = new HashSet<>();

}
