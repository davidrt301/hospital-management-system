package com.davidrt301.medicare.dto.response;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.davidrt301.medicare.model.Status;

public record AttentionResponse(
    @JsonProperty("identificador")
    Long id,
    
    @JsonProperty("fecha")
    LocalDateTime date,
    
    @JsonProperty("descripcion")
    String description,
    
    @JsonProperty("paciente")
    PatientResponse patient,
    
    @JsonProperty("empleado")
    EmployeeResponse employee,
    
    @JsonProperty("estado")
    Status status
) {
}
