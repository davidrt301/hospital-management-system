package com.davidrt301.medicare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.davidrt301.medicare.model.Status;

public record SpecialtyRequest(
    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    @JsonProperty("nombre")
    String name,
    
    @NotNull(message = "El estado es obligatorio")
    @JsonProperty("estado")
    Status status
) {
}
