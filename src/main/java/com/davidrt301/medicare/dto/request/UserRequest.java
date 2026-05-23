package com.davidrt301.medicare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequest(
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @JsonProperty("nombre_usuario")
    String nameUser,
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Length(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
    @JsonProperty("contrasena")
    String password,
    
    @NotNull(message = "El ID de la persona es obligatorio")
    @JsonProperty("id_persona")
    Long personId
) {
}
