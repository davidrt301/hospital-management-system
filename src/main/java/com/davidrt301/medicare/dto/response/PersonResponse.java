package com.davidrt301.medicare.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.davidrt301.medicare.model.Status;

public record PersonResponse(
    @JsonProperty("identificador")
    Long id,
    
    @JsonProperty("nombre")
    String name,
    
    @JsonProperty("correo_electronico")
    String email,
    
    @JsonProperty("estado")
    Status status
) {
}
