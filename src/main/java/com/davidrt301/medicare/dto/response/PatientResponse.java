package com.davidrt301.medicare.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.davidrt301.medicare.model.Role;
import com.davidrt301.medicare.model.Status;

public record PatientResponse(
    @JsonProperty("identificador")
    Long id,
    
    @JsonProperty("persona")
    PersonResponse person,
    
    @JsonProperty("rol")
    Role role,
    
    @JsonProperty("estado")
    Status status
) {
}
