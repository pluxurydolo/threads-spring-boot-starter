package com.pluxurydolo.threads.dto.request;

public record ContainerStatusRequest(
    String containerId,
    String accessToken
) {
}
