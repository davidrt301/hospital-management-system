package com.davidrt301.medicare.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.davidrt301.medicare.model.Status;

public record SpecialtyResponse(
    @JsonProperty("identificador")
    Long id,
    
    @JsonProperty("nombre")
    String name,
    
    @JsonProperty("estado")
    Status status
) {
}
