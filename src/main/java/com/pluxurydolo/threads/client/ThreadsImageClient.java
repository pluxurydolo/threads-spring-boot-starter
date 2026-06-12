package com.pluxurydolo.threads.client;

import com.pluxurydolo.threads.dto.request.PublishMediaRequest;
import com.pluxurydolo.threads.exception.ThreadsImagePublicationException;
import com.pluxurydolo.threads.flow.publish.image.ThreadsImagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThreadsImageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsImageClient.class);

    private final ThreadsImagePublisher threadsImagePublisher;

    public ThreadsImageClient(ThreadsImagePublisher threadsImagePublisher) {
        this.threadsImagePublisher = threadsImagePublisher;
    }

    public Mono<String> publishImage(PublishMediaRequest request) {
        return threadsImagePublisher.publish(request)
            .doOnSuccess(_ -> LOGGER.info("zujy [threads-starter] Изображение успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.error("hlyy [threads-starter] Произошла ошибка при публикации изображения");
                return Mono.error(new ThreadsImagePublicationException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
