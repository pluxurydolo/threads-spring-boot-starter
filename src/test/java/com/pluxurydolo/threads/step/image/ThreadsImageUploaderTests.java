package com.pluxurydolo.threads.step.image;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.ErrorDetails;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.step.ThreadsContainerPublisher;
import com.pluxurydolo.threads.step.ThreadsContainerStatusPoller;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import org.junit.jupiter.api.BeforeEach;
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
class ThreadsImageUploaderTests {

    @Mock
    private ThreadsImageContainerCreator threadsImageContainerCreator;

    @Mock
    private ThreadsContainerStatusPoller threadsContainerStatusPoller;

    @Mock
    private ThreadsContainerPublisher threadsContainerPublisher;

    @Mock
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private ThreadsAuthProperties threadsAuthProperties;

    @InjectMocks
    private ThreadsImageUploader threadsImageUploader;

    @BeforeEach
    void setUp() {
        when(threadsAuthProperties.userId())
            .thenReturn("userId");
    }

    @Test
    void testUpload() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(threadsImageContainerCreator.create(any()))
            .thenReturn(Mono.just(createContainerResponse()));
        when(threadsContainerStatusPoller.poll(any()))
            .thenReturn(Mono.just(""));
        when(threadsContainerPublisher.publish(any()))
            .thenReturn(Mono.just(publishContainerResponse()));

        Mono<String> result = threadsImageUploader.upload(uploadMediaRequest());

        create(result)
            .expectNext("id")
            .verifyComplete();
    }

    @Test
    void testUploadWhenExceptionOccurred() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(threadsImageContainerCreator.create(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsImageUploader.upload(uploadMediaRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static UploadMediaRequest uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }

    private static Tokens tokens() {
        return new Tokens("exchangeToken", "accessToken");
    }

    private static CreateContainerResponse createContainerResponse() {
        return new CreateContainerResponse("id", "mediaType", "status", "statusCode", errorDetails(), "errorMessage");
    }

    private static PublishContainerResponse publishContainerResponse() {
        return new PublishContainerResponse("id", "mediaId", "permalink", errorDetails());
    }

    private static ErrorDetails errorDetails() {
        return new ErrorDetails("message", "type", 1, 1, "fbTraceId");
    }
}
