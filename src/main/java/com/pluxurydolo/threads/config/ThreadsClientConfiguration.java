package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.client.ThreadsClient;
import com.pluxurydolo.threads.step.image.ThreadsImageUploader;
import com.pluxurydolo.threads.step.video.ThreadsVideoUploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsClient threadsClient(
        ThreadsImageUploader threadsImageUploader,
        ThreadsVideoUploader threadsVideoUploader
    ) {
        return new ThreadsClient(threadsImageUploader, threadsVideoUploader);
    }
}
