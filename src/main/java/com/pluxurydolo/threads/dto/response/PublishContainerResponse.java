package com.pluxurydolo.threads.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublishContainerResponse(

    @JsonProperty("id")
    String id,

    @JsonProperty("media_id")
    String mediaId,

    @JsonProperty("permalink")
    String permalink,

    @JsonProperty("error")
    ErrorDetails error
) {
}
