package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.flow.publish.ThreadsContainerPublisher;
import com.pluxurydolo.threads.flow.publish.ThreadsContainerStatusPoller;
import com.pluxurydolo.threads.flow.publish.image.ThreadsImageContainerCreator;
import com.pluxurydolo.threads.flow.publish.image.ThreadsImageUploader;
import com.pluxurydolo.threads.flow.publish.video.ThreadsVideoContainerCreator;
import com.pluxurydolo.threads.flow.publish.video.ThreadsVideoUploader;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsUploadConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsImageUploader threadsImageSender(
        ThreadsImageContainerCreator threadsImageContainerCreator,
        ThreadsContainerStatusPoller threadsContainerStatusPoller,
        ThreadsContainerPublisher threadsContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        ThreadsAuthProperties threadsAuthProperties
    ) {
        return new ThreadsImageUploader(
            threadsImageContainerCreator,
            threadsContainerStatusPoller,
            threadsContainerPublisher,
            abstractTokenRetriever,
            threadsAuthProperties
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsVideoUploader threadsVideoSender(
        ThreadsVideoContainerCreator threadsVideoContainerCreator,
        ThreadsContainerStatusPoller threadsContainerStatusPoller,
        ThreadsContainerPublisher threadsContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        ThreadsAuthProperties threadsAuthProperties
    ) {
        return new ThreadsVideoUploader(
            threadsVideoContainerCreator,
            threadsContainerStatusPoller,
            threadsContainerPublisher,
            abstractTokenRetriever,
            threadsAuthProperties
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsImageContainerCreator threadsImageContainerCreator(ThreadsUploadHttpClient threadsUploadHttpClient) {
        return new ThreadsImageContainerCreator(threadsUploadHttpClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsVideoContainerCreator threadsVideoContainerCreator(ThreadsUploadHttpClient threadsUploadHttpClient) {
        return new ThreadsVideoContainerCreator(threadsUploadHttpClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsContainerStatusPoller threadsContainerStatusPoller(
        ThreadsUploadHttpClient threadsUploadHttpClient,
        ThreadsPollingProperties threadsPollingProperties
    ) {
        return new ThreadsContainerStatusPoller(threadsUploadHttpClient, threadsPollingProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsContainerPublisher threadsContainerPublisher(ThreadsUploadHttpClient threadsUploadHttpClient) {
        return new ThreadsContainerPublisher(threadsUploadHttpClient);
    }
}
