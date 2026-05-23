package com.davidrt301.medicare.dto.request;

import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.davidrt301.medicare.model.Role;
import com.davidrt301.medicare.model.Status;

public record PatientRequest(
    @NotNull(message = "El ID de la persona es obligatorio")
    @JsonProperty("id_persona")
    Long personId,
    
    @NotNull(message = "El rol es obligatorio")
    @JsonProperty("rol")
    Role role,
    
    @NotNull(message = "El estado es obligatorio")
    @JsonProperty("estado")
    Status status
) {
}
