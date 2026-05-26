package com.davidrt301.medicare.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageResponse(

    @JsonProperty("mensaje")
    String message

) {

}
