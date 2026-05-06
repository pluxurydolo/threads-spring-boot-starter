package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class WebTestConfiguration {

    @Bean
    public ThreadsApiHttpClient threadsApiHttpClient() {
        ThreadsApiHttpClient mock = mock(ThreadsApiHttpClient.class);

        when(mock.getExchangeToken(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(mock.getAccessToken(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(mock.refreshToken(anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));

        return mock;
    }

    @Bean
    public ThreadsUploadHttpClient threadsUploadHttpClient() {
        ThreadsUploadHttpClient mock = mock(ThreadsUploadHttpClient.class);

        when(mock.createImageContainer(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(createContainerResponse()));
        when(mock.createVideoContainer(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(createContainerResponse()));
        when(mock.publishContainer(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(publishContainerResponse()));
        when(mock.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerStatusResponse()));

        return mock;
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1, 1L, "error", "errorDescription", "errorType");
    }

    private static CreateContainerResponse createContainerResponse() {
        return new CreateContainerResponse("id", "mediaType", "status", "statusCode", errorDetails(), "errorMessage");
    }

    private static ContainerStatusResponse containerStatusResponse() {
        return new ContainerStatusResponse("id", "FINISHED", "errorMessage", errorDetails());
    }

    private static PublishContainerResponse publishContainerResponse() {
        return new PublishContainerResponse("id", "mediaId", "permalink", errorDetails());
    }

    private static ErrorDetails errorDetails() {
        return new ErrorDetails("message", "type", 1, 1, "fbTraceId");
    }
}
