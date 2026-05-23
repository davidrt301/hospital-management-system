package com.davidrt301.medicare.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MedicalSpecialtyResponse(
    @JsonProperty("identificador")
    Long id,
    
    @JsonProperty("empleado")
    EmployeeResponse employee,
    
    @JsonProperty("especialidad")
    SpecialtyResponse specialty
) {
}
