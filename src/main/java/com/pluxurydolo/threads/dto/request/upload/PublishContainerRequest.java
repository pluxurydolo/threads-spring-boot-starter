package com.pluxurydolo.threads.dto.request.upload;

public record PublishContainerRequest(
    String containerId,
    String userId,
    String accessToken
) {
}
