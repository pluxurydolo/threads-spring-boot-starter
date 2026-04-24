package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import com.pluxurydolo.threads.step.ThreadsContainerPublisher;
import com.pluxurydolo.threads.step.ThreadsContainerStatusPoller;
import com.pluxurydolo.threads.step.image.ThreadsImageContainerCreator;
import com.pluxurydolo.threads.step.image.ThreadsImageUploader;
import com.pluxurydolo.threads.step.video.ThreadsVideoContainerCreator;
import com.pluxurydolo.threads.step.video.ThreadsVideoUploader;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsUploadStepConfiguration {

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
    public ThreadsImageContainerCreator threadsImageContainerCreator(ThreadsUploadWebClient threadsUploadWebClient) {
        return new ThreadsImageContainerCreator(threadsUploadWebClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsVideoContainerCreator threadsVideoContainerCreator(ThreadsUploadWebClient threadsUploadWebClient) {
        return new ThreadsVideoContainerCreator(threadsUploadWebClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsContainerStatusPoller threadsImageContainerStatusPoller(
        ThreadsUploadWebClient threadsUploadWebClient,
        ThreadsPollingProperties threadsPollingProperties
    ) {
        return new ThreadsContainerStatusPoller(threadsUploadWebClient, threadsPollingProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsContainerPublisher threadsImageContainerPublisher(
        ThreadsUploadWebClient threadsUploadWebClient
    ) {
        return new ThreadsContainerPublisher(threadsUploadWebClient);
    }
}
