package com.pluxurydolo.threads.dto.request.security;

public record ExchangeTokenRequest(
    String appId,
    String appSecret,
    String redirectUri,
    String code
) {
}
