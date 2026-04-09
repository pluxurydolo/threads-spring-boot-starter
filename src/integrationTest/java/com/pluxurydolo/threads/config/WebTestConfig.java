package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Clock;

import static java.time.Clock.systemUTC;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class WebTestConfig {

    @Bean
    public ThreadsApiWebClient threadsApiWebClient() {
        ThreadsApiWebClient threadsApiWebClient = mock(ThreadsApiWebClient.class);

        when(threadsApiWebClient.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        return threadsApiWebClient;
    }

    @Bean
    public ThreadsUploadWebClient threadsUploadWebClient() {
        return mock(ThreadsUploadWebClient.class);
    }

    @Bean
    public Clock clock() {
        return systemUTC();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }
}
