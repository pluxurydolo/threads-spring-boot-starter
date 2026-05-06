package com.pluxurydolo.threads.flow.publish;

import com.pluxurydolo.threads.dto.request.PublishContainerRequest;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.exception.ThreadsPublishImageContainerException;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsContainerPublisherTests {

    @Mock
    private ThreadsUploadHttpClient threadsUploadHttpClient;

    @InjectMocks
    private ThreadsContainerPublisher threadsContainerPublisher;

    @Test
    void testPublish() {
        when(threadsUploadHttpClient.publishContainer(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(publishContainerResponse()));

        Mono<PublishContainerResponse> result = threadsContainerPublisher.publish(publishContainerRequest());

        create(result)
            .expectNext(publishContainerResponse())
            .verifyComplete();
    }

    @Test
    void testPublishWhenExceptionOccurred() {
        when(threadsUploadHttpClient.publishContainer(anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<PublishContainerResponse> result = threadsContainerPublisher.publish(publishContainerRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsPublishImageContainerException.class));
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
