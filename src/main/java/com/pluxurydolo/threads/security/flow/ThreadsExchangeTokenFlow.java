package com.pluxurydolo.threads.security.flow;

import com.pluxurydolo.threads.dto.request.security.ExchangeTokenRequest;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import reactor.core.publisher.Mono;

public class ThreadsExchangeTokenFlow {
    private final ThreadsApiWebClient threadsApiWebClient;
    private final ThreadsProperties threadsProperties;

    public ThreadsExchangeTokenFlow(ThreadsApiWebClient threadsApiWebClient, ThreadsProperties threadsProperties) {
        this.threadsApiWebClient = threadsApiWebClient;
        this.threadsProperties = threadsProperties;
    }

    public Mono<TokenResponse> getToken(String code) {
        String appId = threadsProperties.appId();
        String appSecret = threadsProperties.appSecret();
        String redirectUri = threadsProperties.redirectUri();
        ExchangeTokenRequest request = new ExchangeTokenRequest(appId, appSecret, redirectUri, code);

        return threadsApiWebClient.getExchangeToken(request);
    }
}
