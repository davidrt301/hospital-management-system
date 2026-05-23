package com.davidrt301.medicare.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
    @JsonProperty("identificador")
    Long id,
    
    @JsonProperty("nombre_usuario")
    String nameUser,
    
    @JsonProperty("persona")
    PersonResponse person
) {
}
