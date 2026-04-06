package com.pluxurydolo.threads.step;

import com.pluxurydolo.threads.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsContainerPublisherTests {

    @Mock
    private ThreadsUploadWebClient threadsUploadWebClient;

    @InjectMocks
    private ThreadsContainerPublisher threadsContainerPublisher;

    @Test
    void testPublish() {
        when(threadsUploadWebClient.publishContainer(any()))
            .thenReturn(Mono.just(publishContainerResponse()));

        Mono<PublishContainerResponse> result = threadsContainerPublisher.publish(publishContainerRequest());

        create(result)
            .expectNext(publishContainerResponse())
            .verifyComplete();
    }

    @Test
    void testPublishWhenExceptionOccurred() {
        when(threadsUploadWebClient.publishContainer(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<PublishContainerResponse> result = threadsContainerPublisher.publish(publishContainerRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static PublishContainerRequest publishContainerRequest() {
        return new PublishContainerRequest("containerId", "userId", "accessToken");
    }

    private static PublishContainerResponse publishContainerResponse() {
        return new PublishContainerResponse("id", "mediaId", "permalink", errorDetails());
    }

    private static ErrorDetails errorDetails() {
        return new ErrorDetails("message", "type", 1, 1, "fbTraceId");
    }
}
