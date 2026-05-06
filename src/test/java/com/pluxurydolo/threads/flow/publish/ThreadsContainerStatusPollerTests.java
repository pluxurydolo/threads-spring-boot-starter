package com.pluxurydolo.threads.flow.publish;

import com.pluxurydolo.threads.dto.request.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.exception.ThreadsImageContainerStatusException;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsContainerStatusPollerTests {

    @Mock
    private ThreadsUploadHttpClient threadsUploadHttpClient;

    @Mock
    private ThreadsPollingProperties threadsPollingProperties;

    @InjectMocks
    private ThreadsContainerStatusPoller threadsContainerStatusPoller;

    @Test
    void testPoll() {
        when(threadsPollingProperties.delay())
            .thenReturn(Duration.ofSeconds(1));
        when(threadsPollingProperties.maxRepeat())
            .thenReturn(100);
        when(threadsUploadHttpClient.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerStatusResponse()));

        Mono<String> result = threadsContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .expectNext("FINISHED")
            .verifyComplete();
    }

    @Test
    void testPollWhenExceptionOccurred() {
        when(threadsPollingProperties.delay())
            .thenReturn(Duration.ofSeconds(1));
        when(threadsPollingProperties.maxRepeat())
            .thenReturn(100);
        when(threadsUploadHttpClient.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsImageContainerStatusException.class));
    }

    private static ContainerStatusRequest createContainerStatusRequest() {
        return new ContainerStatusRequest("containerId", "accessToken");
    }

    private static ContainerStatusResponse containerStatusResponse() {
        return new ContainerStatusResponse("id", "FINISHED", "errorMessage", errorDetails());
    }

    private static ErrorDetails errorDetails() {
        return new ErrorDetails("message", "type", 1, 1, "fbTraceId");
    }
}
