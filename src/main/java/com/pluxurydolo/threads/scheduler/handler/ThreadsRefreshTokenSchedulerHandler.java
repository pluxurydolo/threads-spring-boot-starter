package com.pluxurydolo.threads.scheduler.handler;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.threads.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsRefreshTokenSchedulerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsRefreshTokenSchedulerHandler.class);

    private final ThreadsRefreshTokenFlow threadsRefreshTokenFlow;
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook;

    public ThreadsRefreshTokenSchedulerHandler(
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever,
        RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook
    ) {
        this.threadsRefreshTokenFlow = threadsRefreshTokenFlow;
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.refreshTokenSchedulerHandlerHook = refreshTokenSchedulerHandlerHook;
    }

    public Mono<String> handle(String jobName) {
        LOGGER.info("dwus Стартовала джоба {}", jobName);

        return abstractTokenRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(threadsRefreshTokenFlow::refreshToken)
            .flatMap(_ -> refreshTokenSchedulerHandlerHook.doAfter())
            .doOnSuccess(_ -> LOGGER.info("bgmk Джоба {} успешно завершена", jobName))
            .onErrorResume(throwable -> {
                LOGGER.error("itet Джоба {} успешно завершена", jobName);
                return refreshTokenSchedulerHandlerHook.handleException(throwable, jobName);
            });
    }
}
