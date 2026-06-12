package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.PublishMediaRequest;
import com.pluxurydolo.threads.exception.ThreadsVideoPublicationException;
import com.pluxurydolo.threads.flow.publish.video.ThreadsVideoPublisher;
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
class ThreadsVideoClientTests {

    @Mock
    private ThreadsVideoPublisher threadsVideoPublisher;

    @InjectMocks
    private ThreadsVideoClient threadsVideoClient;

    @Test
    void testPublishVideo() {
        when(threadsVideoPublisher.publish(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsVideoClient.publishVideo(uploadMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testPublishVideoWhenExceptionOccurred() {
        when(threadsVideoPublisher.publish(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsVideoClient.publishVideo(uploadMediaRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ThreadsVideoPublicationException.class));
    }

    private static PublishMediaRequest uploadMediaRequest() {
        return new PublishMediaRequest("mediaUrl", "caption");
    }
}
