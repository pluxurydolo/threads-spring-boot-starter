package com.pluxurydolo.threads.step.video;

import com.pluxurydolo.threads.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
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
class ThreadsVideoContainerCreatorTests {

    @Mock
    private ThreadsUploadWebClient threadsUploadWebClient;

    @InjectMocks
    private ThreadsVideoContainerCreator threadsVideoContainerCreator;

    @Test
    void testCreate() {
        when(threadsUploadWebClient.createVideoContainer(any()))
            .thenReturn(Mono.just(createContainerResponse()));

        Mono<CreateContainerResponse> result = threadsVideoContainerCreator.create(createContainerRequest());

        create(result)
            .expectNext(createContainerResponse())
            .verifyComplete();
    }

    @Test
    void testCreateWhenExceptionOccurred() {
        when(threadsUploadWebClient.createVideoContainer(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<CreateContainerResponse> result = threadsVideoContainerCreator.create(createContainerRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static CreateContainerRequest createContainerRequest() {
        return new CreateContainerRequest("mediaUrl", "caption", "userId", "accessToken");
    }

    private static CreateContainerResponse createContainerResponse() {
        return new CreateContainerResponse("id", "mediaType", "status", "statusCode", errorDetails(), "errorMessage");
    }

    private static ErrorDetails errorDetails() {
        return new ErrorDetails("message", "type", 1, 1, "fbTraceId");
    }
}
