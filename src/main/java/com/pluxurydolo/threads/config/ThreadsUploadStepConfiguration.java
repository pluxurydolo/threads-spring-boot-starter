package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.properties.PollingProperties;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import com.pluxurydolo.threads.step.ThreadsContainerPublisher;
import com.pluxurydolo.threads.step.ThreadsContainerStatusPoller;
import com.pluxurydolo.threads.step.image.ThreadsImageContainerCreator;
import com.pluxurydolo.threads.step.image.ThreadsImageUploader;
import com.pluxurydolo.threads.step.video.ThreadsVideoContainerCreator;
import com.pluxurydolo.threads.step.video.ThreadsVideoUploader;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsUploadStepConfiguration {

    @Bean
    public ThreadsImageUploader threadsImageSender(
        ThreadsImageContainerCreator threadsImageContainerCreator,
        ThreadsContainerStatusPoller threadsContainerStatusPoller,
        ThreadsContainerPublisher threadsContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        ThreadsProperties threadsProperties
    ) {
        return new ThreadsImageUploader(
            threadsImageContainerCreator,
            threadsContainerStatusPoller,
            threadsContainerPublisher,
            abstractTokenRetriever,
            threadsProperties
        );
    }

    @Bean
    public ThreadsVideoUploader threadsVideoSender(
        ThreadsVideoContainerCreator threadsVideoContainerCreator,
        ThreadsContainerStatusPoller threadsContainerStatusPoller,
        ThreadsContainerPublisher threadsContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        ThreadsProperties threadsProperties
    ) {
        return new ThreadsVideoUploader(
            threadsVideoContainerCreator,
            threadsContainerStatusPoller,
            threadsContainerPublisher,
            abstractTokenRetriever,
            threadsProperties
        );
    }

    @Bean
    public ThreadsImageContainerCreator threadsImageContainerCreator(ThreadsUploadWebClient threadsUploadWebClient) {
        return new ThreadsImageContainerCreator(threadsUploadWebClient);
    }

    @Bean
    public ThreadsVideoContainerCreator threadsVideoContainerCreator(ThreadsUploadWebClient threadsUploadWebClient) {
        return new ThreadsVideoContainerCreator(threadsUploadWebClient);
    }

    @Bean
    public ThreadsContainerStatusPoller threadsImageContainerStatusPoller(
        ThreadsUploadWebClient threadsUploadWebClient,
        PollingProperties pollingProperties
    ) {
        return new ThreadsContainerStatusPoller(threadsUploadWebClient, pollingProperties);
    }

    @Bean
    public ThreadsContainerPublisher threadsImageContainerPublisher(
        ThreadsUploadWebClient threadsUploadWebClient
    ) {
        return new ThreadsContainerPublisher(threadsUploadWebClient);
    }
}
