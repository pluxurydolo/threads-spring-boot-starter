package com.pluxurydolo.threads.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContainerStatusResponse(

    @JsonProperty("id")
    String id,

    @JsonProperty("status")
    String status,

    @JsonProperty("error_message")
    String errorMessage,

    @JsonProperty("error")
    ErrorDetails error
) {
}
