package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.dto.ThreadsTokens;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
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
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @Mock
    private RefreshTokenFlowHook refreshTokenFlowHook;

    @InjectMocks
    private ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    @Test
    void testRefreshToken() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(threadsTokens()));
        when(threadsApiHttpClient.refreshToken(anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokenSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));
        when(refreshTokenFlowHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsRefreshTokenFlow.refreshToken();

        create(result)
            .expectNext("SUCCESS")
            .verifyComplete();
    }

    @Test
    void testRefreshTokenWhenExceptionOccurred() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(threadsTokens()));
        when(threadsApiHttpClient.refreshToken(anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));
        when(refreshTokenFlowHook.handleException(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsRefreshTokenFlow.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static ThreadsTokens threadsTokens() {
        return new ThreadsTokens("exchangeToken", "accessToken");
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
