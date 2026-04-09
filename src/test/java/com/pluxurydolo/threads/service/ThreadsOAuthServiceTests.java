package com.pluxurydolo.threads.service;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Mock
    private HttpHeaders httpHeaders;

    @InjectMocks
    private ThreadsOAuthService threadsOAuthService;

    @Test
    void testLogin() {
        doNothing()
            .when(httpHeaders).setLocation(any());
        when(threadsAuthorizationCodeFlow.getAuthorizationUrl())
            .thenReturn("authorizationUrl");
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.getHeaders())
            .thenReturn(httpHeaders);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsOAuthService.login(serverWebExchange);

        create(result)
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
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(threadsRefreshTokenFlow.refreshToken(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsOAuthService.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }

    private static Tokens tokens() {
        return new Tokens("exchangeToken", "accessToken");
    }
}
