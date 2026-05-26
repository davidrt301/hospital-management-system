package com.davidrt301.medicare.dto.request;

import org.hibernate.validator.constraints.Length;

import com.davidrt301.medicare.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRegisterRequest(

    @JsonProperty("nombre")
    @NotBlank(message = "El nombre es obligatorio")
    String userName,

    @JsonProperty("contrasena")
    @NotBlank(message = "La contraseña es obligatoria")
    @Length(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
    String password,
    
    @JsonProperty("rol")
    @NotNull(message = "El rol es obligatorio")
    Role role,
    
    @JsonProperty("id_persona")
    @NotNull(message = "El ID de la persona es obligatorio")
    Long personId
) {

}
