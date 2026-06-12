package com.pluxurydolo.threads.service;

import com.pluxurydolo.threads.flow.oauth.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ThreadsOAuthService {
    private final ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow;
    private final ThreadsAccessTokenFlow threadsAccessTokenFlow;
    private final ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    public ThreadsOAuthService(
        ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow,
        ThreadsAccessTokenFlow threadsAccessTokenFlow,
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow
    ) {
        this.threadsAuthorizationCodeFlow = threadsAuthorizationCodeFlow;
        this.threadsAccessTokenFlow = threadsAccessTokenFlow;
        this.threadsRefreshTokenFlow = threadsRefreshTokenFlow;
    }

    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        ServerHttpResponse response = threadsAuthorizationCodeFlow.getResponse(serverWebExchange);
        return response.setComplete();
    }

    public Mono<String> redirect(String code) {
        return threadsAccessTokenFlow.getAccessToken(code);
    }

    public Mono<String> refreshToken() {
        return threadsRefreshTokenFlow.refreshToken();
    }
}
