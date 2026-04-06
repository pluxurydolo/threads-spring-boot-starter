package com.pluxurydolo.threads.dto.request.upload;

public record ContainerStatusRequest(
    String containerId,
    String accessToken
) {
}
