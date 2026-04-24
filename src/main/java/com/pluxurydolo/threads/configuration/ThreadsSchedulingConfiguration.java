package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.scheduler.ThreadsRefreshTokenScheduler;
import com.pluxurydolo.threads.scheduler.handler.ThreadsRefreshTokenSchedulerHandler;
import com.pluxurydolo.threads.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.threads.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ThreadsSchedulingConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsRefreshTokenScheduler threadsRefreshTokenScheduler(
        ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler
    ) {
        return new ThreadsRefreshTokenScheduler(threadsRefreshTokenSchedulerHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler(
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever,
        RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook
    ) {
        return new ThreadsRefreshTokenSchedulerHandler(
            threadsRefreshTokenFlow,
            abstractTokenRetriever,
            refreshTokenSchedulerHandlerHook
        );
    }
}
