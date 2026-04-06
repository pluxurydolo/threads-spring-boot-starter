package com.pluxurydolo.threads.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(

    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("token_type")
    String tokenType,

    @JsonProperty("expires_in")
    Integer expiresIn,

    @JsonProperty("user_id")
    Long userId,

    @JsonProperty("error")
    String error,

    @JsonProperty("error_description")
    String errorDescription,

    @JsonProperty("error_type")
    String errorType
) {
}
