package com.pluxurydolo.threads.flow;

import com.pluxurydolo.threads.dto.request.token.RefreshTokenRequest;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import reactor.core.publisher.Mono;

public class ThreadsRefreshTokenFlow {
    private final ThreadsApiWebClient threadsApiWebClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public ThreadsRefreshTokenFlow(ThreadsApiWebClient threadsApiWebClient, AbstractTokenSaver abstractTokenSaver) {
        this.threadsApiWebClient = threadsApiWebClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> refreshToken(String accessToken) {
        RefreshTokenRequest request = new RefreshTokenRequest(accessToken);

        return threadsApiWebClient.refreshToken(request)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, accessToken));
    }
}
