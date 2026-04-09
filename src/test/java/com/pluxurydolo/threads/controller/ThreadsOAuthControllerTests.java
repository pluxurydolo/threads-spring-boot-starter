package com.pluxurydolo.threads.controller;

import com.pluxurydolo.threads.service.ThreadsOAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsOAuthControllerTests {

    @Mock
    private ThreadsOAuthService threadsOAuthService;

    @Mock
    private ServerWebExchange serverWebExchange;

    @InjectMocks
    private ThreadsOAuthController threadsOAuthController;

    @Test
    void testLogin() {
        when(threadsOAuthService.login(serverWebExchange))
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsOAuthController.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testCallback() {
        when(threadsOAuthService.callback(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsOAuthController.callback("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(threadsOAuthService.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsOAuthController.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
