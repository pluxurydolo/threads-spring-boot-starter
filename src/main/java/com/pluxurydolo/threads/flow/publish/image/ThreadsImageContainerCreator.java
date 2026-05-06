package com.pluxurydolo.threads.flow.publish.image;

import com.pluxurydolo.threads.dto.request.CreateContainerRequest;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.exception.ThreadsCreateImageContainerException;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsImageContainerCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsImageContainerCreator.class);

    private final ThreadsUploadHttpClient threadsUploadHttpClient;

    public ThreadsImageContainerCreator(ThreadsUploadHttpClient threadsUploadHttpClient) {
        this.threadsUploadHttpClient = threadsUploadHttpClient;
    }

    public Mono<CreateContainerResponse> create(CreateContainerRequest request) {
        String imageUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();
        String mediaType = "IMAGE";

        return threadsUploadHttpClient.createImageContainer(userId, mediaType, imageUrl, accessToken, caption)
            .doOnSuccess(_ -> LOGGER.info("ilau [threads-starter] Контейнер изображения {} успешно создан", imageUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("yjwz [threads-starter] Произошла ошибка при создании контейнера изображения {}", imageUrl);
                return Mono.error(new ThreadsCreateImageContainerException(throwable));
            });
    }
}
