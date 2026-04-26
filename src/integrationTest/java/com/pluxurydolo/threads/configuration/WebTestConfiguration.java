package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
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
        ThreadsApiWebClient mock = mock(ThreadsApiWebClient.class);

        when(mock.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        when(mock.getExchangeToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        when(mock.refreshToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        return mock;
    }

    @Bean
    public ThreadsUploadWebClient threadsUploadWebClient() {
        ThreadsUploadWebClient mock = mock(ThreadsUploadWebClient.class);

        when(mock.createImageContainer(any()))
            .thenReturn(Mono.just(createContainerResponse()));

        when(mock.createVideoContainer(any()))
            .thenReturn(Mono.just(createContainerResponse()));

        when(mock.publishContainer(any()))
            .thenReturn(Mono.just(publishContainerResponse()));

        when(mock.getContainerStatus(any()))
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
