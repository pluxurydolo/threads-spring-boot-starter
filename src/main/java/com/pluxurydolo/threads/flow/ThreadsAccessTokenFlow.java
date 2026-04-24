package com.pluxurydolo.threads.flow;

import com.pluxurydolo.threads.dto.request.token.AccessTokenRequest;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import reactor.core.publisher.Mono;

public class ThreadsAccessTokenFlow {
    private final ThreadsAuthProperties threadsAuthProperties;
    private final ThreadsApiWebClient threadsApiWebClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public ThreadsAccessTokenFlow(
        ThreadsAuthProperties threadsAuthProperties,
        ThreadsApiWebClient threadsApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.threadsAuthProperties = threadsAuthProperties;
        this.threadsApiWebClient = threadsApiWebClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appId = threadsAuthProperties.appId();
        String appSecret = threadsAuthProperties.appSecret();
        AccessTokenRequest request = new AccessTokenRequest(appId, appSecret, exchangeToken);

        return threadsApiWebClient.getAccessToken(request)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, exchangeToken));
    }
}
