package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.exception.ThreadsAccessTokenFlowException;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.junit.jupiter.api.BeforeEach;
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
class ThreadsAccessTokenFlowTests {

    @Mock
    private ThreadsAuthProperties threadsAuthProperties;

    @Mock
    private ThreadsApiHttpClient threadsApiHttpClient;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @InjectMocks
    private ThreadsAccessTokenFlow threadsAccessTokenFlow;

    @BeforeEach
    void setUp() {
        when(threadsAuthProperties.appSecret())
            .thenReturn("appSecret");
    }

    @Test
    void testGetToken() {
        when(threadsApiHttpClient.getAccessToken(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokenSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsAccessTokenFlow.getToken("exchangeToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testGetTokenWhenExceptionOccurred() {
        when(threadsApiHttpClient.getAccessToken(anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsAccessTokenFlow.getToken("exchangeToken");

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsAccessTokenFlowException.class));
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
