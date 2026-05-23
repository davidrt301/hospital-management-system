package com.davidrt301.medicare.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.davidrt301.medicare.model.Status;

public record AttentionRequest(
    @NotNull(message = "La fecha es obligatoria")
    @JsonProperty("fecha")
    LocalDateTime date,
    
    @NotBlank(message = "La descripción es obligatoria")
    @JsonProperty("descripcion")
    String description,
    
    @NotNull(message = "El ID del paciente es obligatorio")
    @JsonProperty("id_paciente")
    Long patientId,
    
    @NotNull(message = "El ID del empleado es obligatorio")
    @JsonProperty("id_empleado")
    Long employeeId,
    
    @NotNull(message = "El estado es obligatorio")
    @JsonProperty("estado")
    Status status
) {
}
