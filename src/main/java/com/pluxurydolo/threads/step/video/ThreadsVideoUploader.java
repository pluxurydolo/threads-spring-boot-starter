package com.pluxurydolo.threads.step.video;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.threads.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.exception.VideoSenderException;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.security.token.AbstractTokensRetriever;
import com.pluxurydolo.threads.step.ThreadsContainerPublisher;
import com.pluxurydolo.threads.step.ThreadsContainerStatusPoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsVideoUploader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsVideoUploader.class);

    private final ThreadsVideoContainerCreator threadsVideoContainerCreator;
    private final ThreadsContainerStatusPoller threadsContainerStatusPoller;
    private final ThreadsContainerPublisher threadsContainerPublisher;
    private final AbstractTokensRetriever abstractTokensRetriever;
    private final ThreadsProperties threadsProperties;

    public ThreadsVideoUploader(
        ThreadsVideoContainerCreator threadsVideoContainerCreator,
        ThreadsContainerStatusPoller threadsContainerStatusPoller,
        ThreadsContainerPublisher threadsContainerPublisher,
        AbstractTokensRetriever abstractTokensRetriever,
        ThreadsProperties threadsProperties
    ) {
        this.threadsVideoContainerCreator = threadsVideoContainerCreator;
        this.threadsContainerStatusPoller = threadsContainerStatusPoller;
        this.threadsContainerPublisher = threadsContainerPublisher;
        this.abstractTokensRetriever = abstractTokensRetriever;
        this.threadsProperties = threadsProperties;
    }

    public Mono<String> upload(UploadMediaRequest request) {
        String videoUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = threadsProperties.userId();

        return abstractTokensRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(accessToken -> uploadVideo(videoUrl, caption, userId, accessToken))
            .map(PublishContainerResponse::id)
            .doOnSuccess(_ -> LOGGER.info("qnoh [threads-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> Mono.error(new VideoSenderException(throwable)));
    }

    private Mono<PublishContainerResponse> uploadVideo(String videoUrl, String caption, String userId, String accessToken) {
        CreateContainerRequest request = new CreateContainerRequest(videoUrl, caption, userId, accessToken);

        return threadsVideoContainerCreator.create(request)
            .map(CreateContainerResponse::id)
            .flatMap(containerId -> publishContainer(containerId, userId, accessToken));
    }

    private Mono<PublishContainerResponse> publishContainer(String containerId, String userId, String accessToken) {
        ContainerStatusRequest request = new ContainerStatusRequest(containerId, accessToken);

        return threadsContainerStatusPoller.poll(request)
            .map(_ -> new PublishContainerRequest(containerId, userId, accessToken))
            .flatMap(threadsContainerPublisher::publish);
    }
}
