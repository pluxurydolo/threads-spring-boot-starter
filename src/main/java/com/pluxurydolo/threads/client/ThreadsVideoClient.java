package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.threads.exception.ThreadsVideoUploadException;
import com.pluxurydolo.threads.step.video.ThreadsVideoUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThreadsVideoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsVideoClient.class);

    private final ThreadsVideoUploader threadsVideoUploader;

    public ThreadsVideoClient(ThreadsVideoUploader threadsVideoUploader) {
        this.threadsVideoUploader = threadsVideoUploader;
    }

    public Mono<String> uploadVideo(UploadMediaRequest request) {
        return threadsVideoUploader.upload(request)
            .doOnSuccess(_ -> LOGGER.info("qnoh [threads-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.error("uhgv [threads-starter] Произошла ошибка при публикации видео");
                return Mono.error(new ThreadsVideoUploadException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
