package com.pluxurydolo.threads.flow.publish.video;

import com.pluxurydolo.threads.dto.request.CreateContainerRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.exception.ThreadsCreateImageContainerException;
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
class ThreadsVideoContainerCreatorTests {

    @Mock
    private ThreadsUploadHttpClient threadsUploadHttpClient;

    @InjectMocks
    private ThreadsVideoContainerCreator threadsVideoContainerCreator;

    @Test
    void testCreate() {
        when(threadsUploadHttpClient.createVideoContainer(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(createContainerResponse()));

        Mono<CreateContainerResponse> result = threadsVideoContainerCreator.create(createContainerRequest());

        create(result)
            .expectNext(createContainerResponse())
            .verifyComplete();
    }

    @Test
    void testCreateWhenExceptionOccurred() {
        when(threadsUploadHttpClient.createVideoContainer(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<CreateContainerResponse> result = threadsVideoContainerCreator.create(createContainerRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsCreateImageContainerException.class));
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
