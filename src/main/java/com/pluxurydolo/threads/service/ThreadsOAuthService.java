package com.pluxurydolo.threads.service;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.FOUND;

public class ThreadsOAuthService {
    private final ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow;
    private final ThreadsExchangeTokenFlow threadsExchangeTokenFlow;
    private final ThreadsAccessTokenFlow threadsAccessTokenFlow;
    private final ThreadsRefreshTokenFlow threadsRefreshTokenFlow;
    private final AbstractTokenRetriever abstractTokenRetriever;

    public ThreadsOAuthService(
        ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow,
        ThreadsExchangeTokenFlow threadsExchangeTokenFlow,
        ThreadsAccessTokenFlow threadsAccessTokenFlow,
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever
    ) {
        this.threadsAuthorizationCodeFlow = threadsAuthorizationCodeFlow;
        this.threadsExchangeTokenFlow = threadsExchangeTokenFlow;
        this.threadsAccessTokenFlow = threadsAccessTokenFlow;
        this.threadsRefreshTokenFlow = threadsRefreshTokenFlow;
        this.abstractTokenRetriever = abstractTokenRetriever;
    }

    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        String authorizationUrl = threadsAuthorizationCodeFlow.getAuthorizationUrl();
        URI uri = create(authorizationUrl);

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(FOUND);
        response.getHeaders().setLocation(uri);

        return response.setComplete();
    }

    public Mono<String> redirect(String code) {
        return threadsExchangeTokenFlow.getToken(code)
            .map(TokenResponse::accessToken)
            .flatMap(threadsAccessTokenFlow::getToken)
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> refreshToken() {
        return abstractTokenRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(threadsRefreshTokenFlow::refreshToken)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
