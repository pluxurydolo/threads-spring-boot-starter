package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.threads.step.image.ThreadsImageUploader;
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
class ThreadsImageClientTests {

    @Mock
    private ThreadsImageUploader threadsImageUploader;

    @InjectMocks
    private ThreadsImageClient threadsImageClient;

    @Test
    void testUploadImage() {
        when(threadsImageUploader.upload(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsImageClient.uploadImage(uploadMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testUploadImageWhenExceptionOccurred() {
        when(threadsImageUploader.upload(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsImageClient.uploadImage(uploadMediaRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static UploadMediaRequest uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }
}
