package com.pluxurydolo.threads.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateContainerResponse(

    @JsonProperty("id")
    String id,

    @JsonProperty("media_type")
    String mediaType,

    @JsonProperty("status")
    String status,

    @JsonProperty("status_code")
    String statusCode,

    @JsonProperty("error")
    ErrorDetails error,

    @JsonProperty("error_message")
    String errorMessage
) {
}
