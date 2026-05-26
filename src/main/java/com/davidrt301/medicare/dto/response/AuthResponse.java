package com.davidrt301.medicare.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(

    @JsonProperty("mensaje")
    String message,

    @JsonProperty("token")
    String token,

    @JsonProperty("tipo_token")
    String tokenType
) {
    public AuthResponse(String message) {
        this(message, null, null);
    }
    public AuthResponse(String token, String tokenType) {
        this("Login exitoso", token, tokenType);
    }
    
}