package com.pluxurydolo.threads.flow;

import com.pluxurydolo.threads.dto.request.token.AccessTokenRequest;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import reactor.core.publisher.Mono;

public class ThreadsAccessTokenFlow {
    private final ThreadsProperties threadsProperties;
    private final ThreadsApiWebClient threadsApiWebClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public ThreadsAccessTokenFlow(
        ThreadsProperties threadsProperties,
        ThreadsApiWebClient threadsApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.threadsProperties = threadsProperties;
        this.threadsApiWebClient = threadsApiWebClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appId = threadsProperties.appId();
        String appSecret = threadsProperties.appSecret();
        AccessTokenRequest request = new AccessTokenRequest(appId, appSecret, exchangeToken);

        return threadsApiWebClient.getAccessToken(request)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, exchangeToken));
    }
}
