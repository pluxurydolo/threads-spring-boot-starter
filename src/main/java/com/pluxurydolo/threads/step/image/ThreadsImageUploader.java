package com.pluxurydolo.threads.step.image;

import com.pluxurydolo.threads.dto.Tokens;
import com.pluxurydolo.threads.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.threads.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.step.ThreadsContainerPublisher;
import com.pluxurydolo.threads.step.ThreadsContainerStatusPoller;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import reactor.core.publisher.Mono;

public class ThreadsImageUploader {
    private final ThreadsImageContainerCreator threadsImageContainerCreator;
    private final ThreadsContainerStatusPoller threadsContainerStatusPoller;
    private final ThreadsContainerPublisher threadsContainerPublisher;
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final ThreadsAuthProperties threadsAuthProperties;

    public ThreadsImageUploader(
        ThreadsImageContainerCreator threadsImageContainerCreator,
        ThreadsContainerStatusPoller threadsContainerStatusPoller,
        ThreadsContainerPublisher threadsContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        ThreadsAuthProperties threadsAuthProperties
    ) {
        this.threadsImageContainerCreator = threadsImageContainerCreator;
        this.threadsContainerStatusPoller = threadsContainerStatusPoller;
        this.threadsContainerPublisher = threadsContainerPublisher;
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.threadsAuthProperties = threadsAuthProperties;
    }

    public Mono<String> upload(UploadMediaRequest request) {
        String imageUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = threadsAuthProperties.userId();

        return abstractTokenRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(accessToken -> uploadImage(imageUrl, caption, userId, accessToken))
            .map(PublishContainerResponse::id);
    }

    private Mono<PublishContainerResponse> uploadImage(String imageUrl, String caption, String userId, String accessToken) {
        CreateContainerRequest request = new CreateContainerRequest(imageUrl, caption, userId, accessToken);

        return threadsImageContainerCreator.create(request)
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
