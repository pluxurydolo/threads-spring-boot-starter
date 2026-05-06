package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.exception.ThreadsExchangeTokenFlowException;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.junit.jupiter.api.BeforeEach;
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
class ThreadsExchangeTokenFlowTests {

    @Mock
    private ThreadsApiHttpClient threadsApiHttpClient;

    @Mock
    private ThreadsAuthProperties threadsAuthProperties;

    @InjectMocks
    private ThreadsExchangeTokenFlow threadsExchangeTokenFlow;

    @BeforeEach
    void setUp() {
        when(threadsAuthProperties.appId())
            .thenReturn("appId");
        when(threadsAuthProperties.appSecret())
            .thenReturn("appSecret");
        when(threadsAuthProperties.redirectUri())
            .thenReturn("redirectUri");
    }

    @Test
    void testGetToken() {
        when(threadsApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));

        Mono<TokenResponse> result = threadsExchangeTokenFlow.getToken("code");

        create(result)
            .expectNext(tokenResponse())
            .verifyComplete();
    }

    @Test
    void testGetTokenWhenExceptionOccurred() {
        when(threadsApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<TokenResponse> result = threadsExchangeTokenFlow.getToken("code");

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsExchangeTokenFlowException.class));
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
