package com.pluxurydolo.threads.scheduler.handler;

import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsRefreshTokenSchedulerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsRefreshTokenSchedulerHandler.class);

    private final ThreadsRefreshTokenFlow threadsRefreshTokenFlow;

    public ThreadsRefreshTokenSchedulerHandler(ThreadsRefreshTokenFlow threadsRefreshTokenFlow) {
        this.threadsRefreshTokenFlow = threadsRefreshTokenFlow;
    }

    public Mono<String> handle(String jobName) {
        LOGGER.info("dwus [threads-starter] Стартовала джоба {}", jobName);

        return threadsRefreshTokenFlow.refreshToken()
            .doOnSuccess(_ -> LOGGER.info("bgmk [threads-starter] Джоба {} успешно завершена", jobName))
            .doOnError(_ -> LOGGER.error("itet [threads-starter] Произошла ошибка при завершении джобы {}", jobName));
    }
}
