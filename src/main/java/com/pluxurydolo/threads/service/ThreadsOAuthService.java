package com.pluxurydolo.threads.service;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.security.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.security.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.security.token.AbstractTokensRetriever;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

import static org.springframework.http.HttpStatus.FOUND;

public class ThreadsOAuthService {
    private final ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow;
    private final ThreadsExchangeTokenFlow threadsExchangeTokenFlow;
    private final ThreadsAccessTokenFlow threadsAccessTokenFlow;
    private final ThreadsRefreshTokenFlow  threadsRefreshTokenFlow;
    private final AbstractTokensRetriever abstractTokensRetriever;

    public ThreadsOAuthService(
        ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow,
        ThreadsExchangeTokenFlow threadsExchangeTokenFlow,
        ThreadsAccessTokenFlow threadsAccessTokenFlow,
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow,
        AbstractTokensRetriever abstractTokensRetriever
    ) {
        this.threadsAuthorizationCodeFlow = threadsAuthorizationCodeFlow;
        this.threadsExchangeTokenFlow = threadsExchangeTokenFlow;
        this.threadsAccessTokenFlow = threadsAccessTokenFlow;
        this.threadsRefreshTokenFlow = threadsRefreshTokenFlow;
        this.abstractTokensRetriever = abstractTokensRetriever;
    }

    public Mono<ResponseEntity<Void>> login() {
        String authUrl = threadsAuthorizationCodeFlow.getAuthorizationUrl();
        URI uri = URI.create(authUrl);

        ResponseEntity<Void> responseEntity = ResponseEntity.status(FOUND)
            .location(uri)
            .build();

        return Mono.just(responseEntity);
    }

    public Mono<String> callback(String code) {
        return threadsExchangeTokenFlow.getToken(code)
            .map(TokenResponse::accessToken)
            .flatMap(threadsAccessTokenFlow::getToken)
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> refreshToken() {
        return abstractTokensRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(threadsRefreshTokenFlow::refreshToken)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
