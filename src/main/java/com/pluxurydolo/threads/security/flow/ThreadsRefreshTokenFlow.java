package com.pluxurydolo.threads.security.flow;

import reactor.core.publisher.Mono;

public class ThreadsRefreshTokenFlow {
    private final ThreadsAccessTokenFlow threadsAccessTokenFlow;

    public ThreadsRefreshTokenFlow(ThreadsAccessTokenFlow threadsAccessTokenFlow) {
        this.threadsAccessTokenFlow = threadsAccessTokenFlow;
    }

    public Mono<String> refreshToken(String currentToken) {
        return threadsAccessTokenFlow.getToken(currentToken);
    }
}
