package com.pluxurydolo.threads.flow.publish.oauth;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.exception.ThreadsRefreshTokenFlowException;
import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsRefreshTokenFlowTests {

    @Mock
    private ThreadsApiHttpClient threadsApiHttpClient;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @InjectMocks
    private ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    @Test
    void testRefreshToken() {
        when(threadsApiHttpClient.refreshToken(anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokenSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsRefreshTokenFlow.refreshToken("currentToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshTokenWhenExceptionOccurred() {
        when(threadsApiHttpClient.refreshToken(anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsRefreshTokenFlow.refreshToken("currentToken");

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsRefreshTokenFlowException.class));
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
