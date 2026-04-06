package com.pluxurydolo.threads.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDetails(

    @JsonProperty("message")
    String message,

    @JsonProperty("type")
    String type,

    @JsonProperty("code")
    Integer code,

    @JsonProperty("error_subcode")
    Integer errorSubcode,

    @JsonProperty("fbtrace_id")
    String fbTraceId
) {
}
