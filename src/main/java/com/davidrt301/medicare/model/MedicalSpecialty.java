package com.davidrt301.medicare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_specialties")
public class MedicalSpecialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "FK_medical_specialty_employee"))
    private Employee employee;

    @ManyToOne
    /**
     * Configura la columna de unión con la entidad Specialty.
     * - name: Nombre de la columna de clave foránea en la tabla.
     * - nullable: Define que la relación es obligatoria a nivel de BD.
     * - foreignKey: Nombra la restricción para facilitar el mantenimiento.
     */
    @JoinColumn(name = "specialty_id", nullable = false, foreignKey = @ForeignKey(name = "FK_medical_specialty_specialty"))
    private Specialty specialty;


}
