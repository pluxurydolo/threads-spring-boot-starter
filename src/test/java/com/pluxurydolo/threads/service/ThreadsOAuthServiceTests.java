package com.pluxurydolo.threads.service;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.security.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.security.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.security.token.AbstractTokensRetriever;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.FOUND;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsOAuthServiceTests {

    @Mock
    private ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow;

    @Mock
    private ThreadsExchangeTokenFlow threadsExchangeTokenFlow;

    @Mock
    private ThreadsAccessTokenFlow threadsAccessTokenFlow;

    @Mock
    private ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    @Mock
    private AbstractTokensRetriever abstractTokensRetriever;

    @InjectMocks
    private ThreadsOAuthService threadsOAuthService;

    @Test
    void testLogin() {
        when(threadsAuthorizationCodeFlow.getAuthorizationUrl())
            .thenReturn("authorizationUrl");

        Mono<ResponseEntity<Void>> result = threadsOAuthService.login();

        create(result)
            .expectNext(responseEntity())
            .verifyComplete();
    }

    @Test
    void testCallback() {
        when(threadsExchangeTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(threadsAccessTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(""));


        Mono<String> result = threadsOAuthService.callback("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(abstractTokensRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(threadsRefreshTokenFlow.refreshToken(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsOAuthService.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static ResponseEntity<Void> responseEntity() {
        URI uri = URI.create("authorizationUrl");

        return ResponseEntity.status(FOUND)
            .location(uri)
            .build();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }

    private static Tokens tokens() {
        return new Tokens("exchangeToken", "accessToken");
    }
}
