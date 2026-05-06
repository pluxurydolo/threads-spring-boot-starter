package com.pluxurydolo.threads.dto.request;

public record CreateContainerRequest(
    String mediaUrl,
    String caption,
    String userId,
    String accessToken
) {
}
