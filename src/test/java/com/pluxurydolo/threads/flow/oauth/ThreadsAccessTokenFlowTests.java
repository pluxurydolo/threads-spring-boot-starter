package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.flow.oauth.hook.AccessTokenFlowHook;
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

    @Mock
    private AccessTokenFlowHook accessTokenFlowHook;

    @InjectMocks
    private ThreadsAccessTokenFlow threadsAccessTokenFlow;

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
    void testGetAccessToken() {
        when(threadsApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(threadsApiHttpClient.getAccessToken(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokenSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));
        when(accessTokenFlowHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsAccessTokenFlow.getAccessToken("exchangeToken");

        create(result)
            .expectNext("SUCCESS")
            .verifyComplete();
    }

    @Test
    void testGetAccessTokenWhenExceptionOccurred() {
        when(threadsApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));
        when(accessTokenFlowHook.handleException(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsAccessTokenFlow.getAccessToken("exchangeToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
