package com.davidrt301.medicare.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @JsonProperty("nombre")
    String name,
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @JsonProperty("correo_electronico")
    String email
) {
}
