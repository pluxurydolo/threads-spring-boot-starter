package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.scheduler.ThreadsRefreshTokenScheduler;
import com.pluxurydolo.threads.scheduler.handler.ThreadsRefreshTokenSchedulerHandler;
import com.pluxurydolo.threads.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.threads.security.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.security.token.AbstractTokensRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ThreadsSchedulingConfiguration {

    @Bean
    public ThreadsRefreshTokenScheduler threadsRefreshTokenScheduler(
        ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler
    ) {
        return new ThreadsRefreshTokenScheduler(threadsRefreshTokenSchedulerHandler);
    }

    @Bean
    public ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler(
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow,
        AbstractTokensRetriever abstractTokensRetriever,
        RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook
    ) {
        return new ThreadsRefreshTokenSchedulerHandler(
            threadsRefreshTokenFlow,
            abstractTokensRetriever,
            refreshTokenSchedulerHandlerHook
        );
    }
}
