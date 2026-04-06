package com.pluxurydolo.threads.security.flow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsRefreshTokenFlowTests {

    @Mock
    private ThreadsAccessTokenFlow threadsAccessTokenFlow;

    @InjectMocks
    private ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    @Test
    void testRefreshToken() {
        when(threadsAccessTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsRefreshTokenFlow.refreshToken("currentToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshTokenWhenExceptionOccurred() {
        when(threadsAccessTokenFlow.getToken(anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsRefreshTokenFlow.refreshToken("currentToken");

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }
}
