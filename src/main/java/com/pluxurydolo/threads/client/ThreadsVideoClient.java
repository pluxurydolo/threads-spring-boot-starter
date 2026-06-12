package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.PublishMediaRequest;
import com.pluxurydolo.threads.exception.ThreadsVideoPublicationException;
import com.pluxurydolo.threads.flow.publish.video.ThreadsVideoPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThreadsVideoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsVideoClient.class);

    private final ThreadsVideoPublisher threadsVideoPublisher;

    public ThreadsVideoClient(ThreadsVideoPublisher threadsVideoPublisher) {
        this.threadsVideoPublisher = threadsVideoPublisher;
    }

    public Mono<String> publishVideo(PublishMediaRequest request) {
        return threadsVideoPublisher.publish(request)
            .doOnSuccess(_ -> LOGGER.info("qnoh [threads-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.error("uhgv [threads-starter] Произошла ошибка при публикации видео");
                return Mono.error(new ThreadsVideoPublicationException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
