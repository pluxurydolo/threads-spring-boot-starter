package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.scheduler.ThreadsRefreshTokenScheduler;
import com.pluxurydolo.threads.scheduler.handler.ThreadsRefreshTokenSchedulerHandler;
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
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow
    ) {
        return new ThreadsRefreshTokenSchedulerHandler(threadsRefreshTokenFlow);
    }
}
