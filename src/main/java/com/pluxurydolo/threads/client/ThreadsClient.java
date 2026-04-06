package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.threads.step.image.ThreadsImageUploader;
import com.pluxurydolo.threads.step.video.ThreadsVideoUploader;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThreadsClient {
    private final ThreadsImageUploader threadsImageUploader;
    private final ThreadsVideoUploader threadsVideoUploader;

    public ThreadsClient(ThreadsImageUploader threadsImageUploader, ThreadsVideoUploader threadsVideoUploader) {
        this.threadsImageUploader = threadsImageUploader;
        this.threadsVideoUploader = threadsVideoUploader;
    }

    public Mono<String> uploadImage(UploadMediaRequest request) {
        return threadsImageUploader.upload(request)
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> uploadVideo(UploadMediaRequest request) {
        return threadsVideoUploader.upload(request)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
