package com.pluxurydolo.threads.step.video;

import com.pluxurydolo.threads.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import reactor.core.publisher.Mono;

public class ThreadsVideoContainerCreator {
    private final ThreadsUploadWebClient threadsUploadWebClient;

    public ThreadsVideoContainerCreator(ThreadsUploadWebClient threadsUploadWebClient) {
        this.threadsUploadWebClient = threadsUploadWebClient;
    }

    public Mono<CreateContainerResponse> create(CreateContainerRequest request) {
        return threadsUploadWebClient.createVideoContainer(request);
    }
}
