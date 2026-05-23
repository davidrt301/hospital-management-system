package com.davidrt301.medicare.dto.request;

import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MedicalSpecialtyRequest(
    @NotNull(message = "El ID del empleado es obligatorio")
    @JsonProperty("id_empleado")
    Long employeeId,
    
    @NotNull(message = "El ID de la especialidad es obligatorio")
    @JsonProperty("id_especialidad")
    Long specialtyId
) {
}
