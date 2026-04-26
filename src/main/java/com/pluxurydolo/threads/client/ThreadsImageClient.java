package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.threads.exception.ThreadsImageUploadException;
import com.pluxurydolo.threads.step.image.ThreadsImageUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThreadsImageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsImageClient.class);

    private final ThreadsImageUploader threadsImageUploader;

    public ThreadsImageClient(ThreadsImageUploader threadsImageUploader) {
        this.threadsImageUploader = threadsImageUploader;
    }

    public Mono<String> uploadImage(UploadMediaRequest request) {
        return threadsImageUploader.upload(request)
            .doOnSuccess(_ -> LOGGER.info("zujy [threads-starter] Изображение успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.error("hlyy [threads-starter] Произошла ошибка при публикации изображения");
                return Mono.error(new ThreadsImageUploadException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
