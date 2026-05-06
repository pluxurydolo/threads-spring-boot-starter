package com.pluxurydolo.threads.dto.request;

public record PublishContainerRequest(
    String containerId,
    String userId,
    String accessToken
) {
}
