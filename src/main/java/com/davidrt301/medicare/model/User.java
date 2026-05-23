package com.davidrt301.medicare.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
/**
 * Define la tabla 'users'. 
 * uniqueConstraints: Asegura que no existan dos registros con el mismo 'user_name' 
 * a nivel de base de datos, garantizando la integridad de las credenciales.
 */
@Table(name = "users", 
       uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
    
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;
    
    @Column(nullable = false)
    private String password;

    /**
     * @JoinColumn define la configuración de la columna de unión (Foreign Key).
     * - name: Nombre de la columna en la tabla 'users'.
     * - nullable: Indica que la relación es obligatoria (no permite nulos).
     * - foreignKey: Define el nombre de la restricción de integridad en la BD
     *   para facilitar el mantenimiento y diagnóstico.
     */
    @OneToOne
    @JoinColumn(name = "person_id", nullable=false, foreignKey=@ForeignKey(name = "FK_user_person"))
    private Person person;

}
