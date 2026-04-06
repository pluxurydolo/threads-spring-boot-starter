package com.pluxurydolo.threads.security.flow;

import com.pluxurydolo.threads.dto.request.security.RefreshTokenRequest;
import com.pluxurydolo.threads.security.token.AbstractTokensSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import reactor.core.publisher.Mono;

public class ThreadsRefreshTokenFlow {
    private final ThreadsApiWebClient threadsApiWebClient;
    private final AbstractTokensSaver abstractTokensSaver;

    public ThreadsRefreshTokenFlow(ThreadsApiWebClient threadsApiWebClient, AbstractTokensSaver abstractTokensSaver) {
        this.threadsApiWebClient = threadsApiWebClient;
        this.abstractTokensSaver = abstractTokensSaver;
    }

    public Mono<String> refreshToken(String accessToken) {
        RefreshTokenRequest request = new RefreshTokenRequest(accessToken);

        return threadsApiWebClient.refreshToken(request)
            .flatMap(tokenResponse -> abstractTokensSaver.save(tokenResponse, accessToken));
    }
}
