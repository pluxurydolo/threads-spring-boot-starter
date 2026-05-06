package com.pluxurydolo.threads.flow.publish;

import com.pluxurydolo.threads.dto.request.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.exception.ThreadsImageContainerStatusException;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.function.Function;

public class ThreadsContainerStatusPoller {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsContainerStatusPoller.class);

    private final ThreadsUploadHttpClient threadsUploadHttpClient;
    private final ThreadsPollingProperties threadsPollingProperties;

    public ThreadsContainerStatusPoller(
        ThreadsUploadHttpClient threadsUploadHttpClient,
        ThreadsPollingProperties threadsPollingProperties
    ) {
        this.threadsUploadHttpClient = threadsUploadHttpClient;
        this.threadsPollingProperties = threadsPollingProperties;
    }

    public Mono<String> poll(ContainerStatusRequest request) {
        Duration delay = threadsPollingProperties.delay();
        long delaySeconds = delay.getSeconds();
        int maxRepeat = threadsPollingProperties.maxRepeat();

        Function<Flux<Long>, Publisher<?>> onRepeat = repeat -> repeat
            .doOnNext(repeatNum -> LOGGER.info(
                "mogq [threads-starter] Повторная попытка обработки контейнера произойдет через {} секунд ({}/{})",
                delaySeconds, repeatNum + 1, maxRepeat
            ))
            .delayElements(delay, Schedulers.boundedElastic());

        return Mono.defer(() -> validateContainerStatus(request))
            .repeatWhenEmpty(maxRepeat, onRepeat);
    }

    private Mono<String> validateContainerStatus(ContainerStatusRequest request) {
        String containerId = request.containerId();
        String accessToken = request.accessToken();
        String fields = "status,error_message";

        return threadsUploadHttpClient.getContainerStatus(containerId, fields, accessToken)
            .map(ContainerStatusResponse::status)
            .doOnNext(status -> LOGGER.info("hnlr [threads-starter] Статус контейнера: {}", status))
            .onErrorResume(throwable -> {
                LOGGER.error("tyrx [threads-starter] Произошла ошибка при проверке статуса контейнера {}", containerId);
                return Mono.error(new ThreadsImageContainerStatusException(throwable));
            })
            .filter("FINISHED"::equals);
    }
}
