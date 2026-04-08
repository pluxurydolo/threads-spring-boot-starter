package com.pluxurydolo.threads.dto.request.token;

public record ExchangeTokenRequest(
    String appId,
    String appSecret,
    String redirectUri,
    String code
) {
}
