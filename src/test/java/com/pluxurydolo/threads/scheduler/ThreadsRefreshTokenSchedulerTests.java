package com.pluxurydolo.threads.scheduler;

import com.pluxurydolo.threads.scheduler.handler.ThreadsRefreshTokenSchedulerHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThreadsRefreshTokenSchedulerTests {

    @Mock
    private ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler;

    @InjectMocks
    private ThreadsRefreshTokenScheduler threadsRefreshTokenScheduler;

    @Test
    void testRefreshToken() {
        when(threadsRefreshTokenSchedulerHandler.handle(anyString()))
            .thenReturn(Mono.just(""));

        assertDoesNotThrow(threadsRefreshTokenScheduler::refreshToken);
    }
}
