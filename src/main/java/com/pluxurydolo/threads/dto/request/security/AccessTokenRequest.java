package com.pluxurydolo.threads.dto.request.security;

public record AccessTokenRequest(
    String appId,
    String appSecret,
    String exchangeToken
) {
}
