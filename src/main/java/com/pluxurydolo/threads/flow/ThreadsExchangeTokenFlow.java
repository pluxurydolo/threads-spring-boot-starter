package com.pluxurydolo.threads.flow;

import com.pluxurydolo.threads.dto.request.token.ExchangeTokenRequest;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import reactor.core.publisher.Mono;

public class ThreadsExchangeTokenFlow {
    private final ThreadsApiWebClient threadsApiWebClient;
    private final ThreadsAuthProperties threadsAuthProperties;

    public ThreadsExchangeTokenFlow(ThreadsApiWebClient threadsApiWebClient, ThreadsAuthProperties threadsAuthProperties) {
        this.threadsApiWebClient = threadsApiWebClient;
        this.threadsAuthProperties = threadsAuthProperties;
    }

    public Mono<TokenResponse> getToken(String code) {
        String appId = threadsAuthProperties.appId();
        String appSecret = threadsAuthProperties.appSecret();
        String redirectUri = threadsAuthProperties.redirectUri();
        ExchangeTokenRequest request = new ExchangeTokenRequest(appId, appSecret, redirectUri, code);

        return threadsApiWebClient.getExchangeToken(request);
    }
}
