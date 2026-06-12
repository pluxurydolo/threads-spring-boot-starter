package com.pluxurydolo.threads.scheduler.handler;

import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsRefreshTokenSchedulerHandlerTests {

    @Mock
    private ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    @InjectMocks
    private ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler;

    @Test
    void testHandle() {
        when(threadsRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testHandleWhenExceptionOccurred() {
        when(threadsRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = threadsRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}
