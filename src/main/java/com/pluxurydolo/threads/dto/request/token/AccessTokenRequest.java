package com.pluxurydolo.threads.dto.request.token;

public record AccessTokenRequest(
    String appId,
    String appSecret,
    String exchangeToken
) {
}
