package com.pluxurydolo.threads.step;

import com.pluxurydolo.threads.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsContainerStatusPollerTests {

    @Mock
    private ThreadsUploadWebClient threadsUploadWebClient;

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
        when(threadsUploadWebClient.getContainerStatus(any()))
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
        when(threadsUploadWebClient.getContainerStatus(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
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
