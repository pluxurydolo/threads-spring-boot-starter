package com.pluxurydolo.threads.flow.publish.video;

import com.pluxurydolo.threads.dto.request.CreateContainerRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.exception.ThreadsCreateImageContainerException;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsVideoContainerCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsVideoContainerCreator.class);

    private final ThreadsUploadHttpClient threadsUploadHttpClient;

    public ThreadsVideoContainerCreator(ThreadsUploadHttpClient threadsUploadHttpClient) {
        this.threadsUploadHttpClient = threadsUploadHttpClient;
    }

    public Mono<CreateContainerResponse> create(CreateContainerRequest request) {
        String videoUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();
        String mediaType = "VIDEO";

        return threadsUploadHttpClient.createVideoContainer(userId, mediaType, videoUrl, accessToken, caption)
            .doOnSuccess(_ -> LOGGER.info("rnud [threads-starter] Контейнер видео {} успешно создан", videoUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("sjgf [threads-starter] Произошла ошибка при создании контейнера видео {}", videoUrl);
                return Mono.error(new ThreadsCreateImageContainerException(throwable));
            });
    }
}
