package com.davidrt301.medicare.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(

    @JsonProperty("nombre_usuario")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    String username,
    
    @JsonProperty("contrasena")
    @NotBlank(message = "La contraseña es obligatoria")
    String password
) {

}
