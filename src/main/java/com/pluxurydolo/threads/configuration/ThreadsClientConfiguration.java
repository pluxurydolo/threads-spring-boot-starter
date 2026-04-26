package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.client.ThreadsImageClient;
import com.pluxurydolo.threads.client.ThreadsVideoClient;
import com.pluxurydolo.threads.step.image.ThreadsImageUploader;
import com.pluxurydolo.threads.step.video.ThreadsVideoUploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsImageClient threadsImageClient(ThreadsImageUploader threadsImageUploader) {
        return new ThreadsImageClient(threadsImageUploader);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsVideoClient threadsVideoClient(ThreadsVideoUploader threadsVideoUploader) {
        return new ThreadsVideoClient(threadsVideoUploader);
    }
}
