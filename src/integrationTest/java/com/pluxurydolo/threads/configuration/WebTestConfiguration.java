package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class WebTestConfiguration {

    @Bean
    public ThreadsApiWebClient threadsApiWebClient() {
        ThreadsApiWebClient threadsApiWebClient = mock(ThreadsApiWebClient.class);

        when(threadsApiWebClient.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        when(threadsApiWebClient.getExchangeToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        when(threadsApiWebClient.refreshToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        return threadsApiWebClient;
    }

    @Bean
    public ThreadsUploadWebClient threadsUploadWebClient() {
        return mock(ThreadsUploadWebClient.class);
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
