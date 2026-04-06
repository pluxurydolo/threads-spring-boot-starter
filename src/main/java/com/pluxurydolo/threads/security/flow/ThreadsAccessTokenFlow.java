package com.pluxurydolo.threads.security.flow;

import com.pluxurydolo.threads.dto.request.security.AccessTokenRequest;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.security.token.AbstractTokensSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import reactor.core.publisher.Mono;

public class ThreadsAccessTokenFlow {
    private final ThreadsProperties threadsProperties;
    private final ThreadsApiWebClient threadsApiWebClient;
    private final AbstractTokensSaver abstractTokensSaver;

    public ThreadsAccessTokenFlow(
        ThreadsProperties threadsProperties,
        ThreadsApiWebClient threadsApiWebClient,
        AbstractTokensSaver abstractTokensSaver
    ) {
        this.threadsProperties = threadsProperties;
        this.threadsApiWebClient = threadsApiWebClient;
        this.abstractTokensSaver = abstractTokensSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appId = threadsProperties.appId();
        String appSecret = threadsProperties.appSecret();
        AccessTokenRequest request = new AccessTokenRequest(appId, appSecret, exchangeToken);

        return threadsApiWebClient.getAccessToken(request)
            .flatMap(tokenResponse -> abstractTokensSaver.save(tokenResponse, exchangeToken));
    }
}
