package com.pluxurydolo.threads.step.image;

import com.pluxurydolo.threads.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import reactor.core.publisher.Mono;

public class ThreadsImageContainerCreator {
    private final ThreadsUploadWebClient threadsUploadWebClient;

    public ThreadsImageContainerCreator(ThreadsUploadWebClient threadsUploadWebClient) {
        this.threadsUploadWebClient = threadsUploadWebClient;
    }

    public Mono<CreateContainerResponse> create(CreateContainerRequest request) {
        return threadsUploadWebClient.createImageContainer(request);
    }
}
