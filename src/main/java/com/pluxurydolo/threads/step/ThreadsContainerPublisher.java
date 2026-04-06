package com.pluxurydolo.threads.step;

import com.pluxurydolo.threads.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import reactor.core.publisher.Mono;

public class ThreadsContainerPublisher {
    private final ThreadsUploadWebClient threadsUploadWebClient;

    public ThreadsContainerPublisher(ThreadsUploadWebClient threadsUploadWebClient) {
        this.threadsUploadWebClient = threadsUploadWebClient;
    }

    public Mono<PublishContainerResponse> publish(PublishContainerRequest request) {
        return threadsUploadWebClient.publishContainer(request);
    }
}
