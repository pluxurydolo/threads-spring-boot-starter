package com.pluxurydolo.threads.security.flow;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.security.token.AbstractTokensSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
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
    private ThreadsProperties threadsProperties;

    @Mock
    private ThreadsApiWebClient threadsApiWebClient;

    @Mock
    private AbstractTokensSaver abstractTokensSaver;

    @InjectMocks
    private ThreadsAccessTokenFlow threadsAccessTokenFlow;

    @Test
    void testGetToken() {
        when(threadsProperties.appId())
            .thenReturn("appId");
        when(threadsProperties.appSecret())
            .thenReturn("appSecret");
        when(threadsApiWebClient.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokensSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsAccessTokenFlow.getToken("exchangeToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testGetTokenWhenExceptionOccurred() {
        when(threadsProperties.appId())
            .thenReturn("appId");
        when(threadsProperties.appSecret())
            .thenReturn("appSecret");
        when(threadsApiWebClient.getAccessToken(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsAccessTokenFlow.getToken("exchangeToken");

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
