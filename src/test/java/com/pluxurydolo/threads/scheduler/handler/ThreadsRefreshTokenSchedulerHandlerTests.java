package com.pluxurydolo.threads.scheduler.handler;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.threads.security.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.security.token.AbstractTokensRetriever;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsRefreshTokenSchedulerHandlerTests {

    @Mock
    private ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    @Mock
    private AbstractTokensRetriever abstractTokensRetriever;

    @Mock
    private RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook;

    @InjectMocks
    private ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler;

    @Test
    void testHandle() {
        when(abstractTokensRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(threadsRefreshTokenFlow.refreshToken(anyString()))
            .thenReturn(Mono.just(""));
        when(refreshTokenSchedulerHandlerHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testHandleWhenExceptionOccurred() {
        when(abstractTokensRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(threadsRefreshTokenFlow.refreshToken(anyString()))
            .thenReturn(Mono.error(new RuntimeException()));
        when(refreshTokenSchedulerHandlerHook.handleException(any(), anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = threadsRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static Tokens tokens() {
        return new Tokens("exchangeToken", "accessToken");
    }
}
