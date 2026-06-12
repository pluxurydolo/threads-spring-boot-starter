package com.pluxurydolo.threads.service;

import com.pluxurydolo.threads.flow.oauth.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsOAuthServiceTests {

    @Mock
    private ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow;

    @Mock
    private ThreadsAccessTokenFlow threadsAccessTokenFlow;

    @Mock
    private ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @InjectMocks
    private ThreadsOAuthService threadsOAuthService;

    @Test
    void testLogin() {
        when(threadsAuthorizationCodeFlow.getResponse(any()))
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsOAuthService.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testRedirect() {
        when(threadsAccessTokenFlow.getAccessToken(anyString()))
            .thenReturn(Mono.just(""));


        Mono<String> result = threadsOAuthService.redirect("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(threadsRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsOAuthService.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
