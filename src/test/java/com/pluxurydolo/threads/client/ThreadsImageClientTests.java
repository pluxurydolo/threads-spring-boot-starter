package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.PublishMediaRequest;
import com.pluxurydolo.threads.exception.ThreadsImagePublicationException;
import com.pluxurydolo.threads.flow.publish.image.ThreadsImagePublisher;
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
    private ThreadsImagePublisher threadsImagePublisher;

    @InjectMocks
    private ThreadsImageClient threadsImageClient;

    @Test
    void testPublishImage() {
        when(threadsImagePublisher.publish(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsImageClient.publishImage(publishMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testPublishImageWhenExceptionOccurred() {
        when(threadsImagePublisher.publish(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsImageClient.publishImage(publishMediaRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsImagePublicationException.class));
    }

    private static PublishMediaRequest publishMediaRequest() {
        return new PublishMediaRequest("mediaUrl", "caption");
    }
}
