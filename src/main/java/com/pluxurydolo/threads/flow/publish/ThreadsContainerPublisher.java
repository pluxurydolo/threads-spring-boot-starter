package com.pluxurydolo.threads.flow.publish;

import com.pluxurydolo.threads.dto.request.PublishContainerRequest;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.exception.ThreadsPublishImageContainerException;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsContainerPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsContainerPublisher.class);

    private final ThreadsUploadHttpClient threadsUploadHttpClient;

    public ThreadsContainerPublisher(ThreadsUploadHttpClient threadsUploadHttpClient) {
        this.threadsUploadHttpClient = threadsUploadHttpClient;
    }

    public Mono<PublishContainerResponse> publish(PublishContainerRequest request) {
        String containerId = request.containerId();
        String userId = request.userId();
        String accessToken = request.accessToken();

        return threadsUploadHttpClient.publishContainer(userId, containerId, accessToken)
            .doOnSuccess(_ -> LOGGER.info("dohz [threads-starter] Контейнер {} успешно опубликован", containerId))
            .onErrorResume(throwable -> {
                LOGGER.error("xrqr [threads-starter] Произошла ошибка при публикации контейнера {}", containerId);
                return Mono.error(new ThreadsPublishImageContainerException(throwable));
            });
    }
}
