package com.pluxurydolo.threads.scheduler.hook;

import reactor.core.publisher.Mono;

public interface RefreshTokenSchedulerHandlerHook {
    Mono<String> doAfter();

    Mono<String> handleException(Throwable throwable, String jobName);
}
