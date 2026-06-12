package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.client.ThreadsImageClient;
import com.pluxurydolo.threads.client.ThreadsVideoClient;
import com.pluxurydolo.threads.flow.publish.image.ThreadsImagePublisher;
import com.pluxurydolo.threads.flow.publish.video.ThreadsVideoPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsImageClient threadsImageClient(ThreadsImagePublisher threadsImagePublisher) {
        return new ThreadsImageClient(threadsImagePublisher);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsVideoClient threadsVideoClient(ThreadsVideoPublisher threadsVideoPublisher) {
        return new ThreadsVideoClient(threadsVideoPublisher);
    }
}
