package com.pluxurydolo.threads.step;

import com.pluxurydolo.threads.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.properties.PollingProperties;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
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

    private final ThreadsUploadWebClient threadsUploadWebClient;
    private final PollingProperties pollingProperties;

    public ThreadsContainerStatusPoller(
        ThreadsUploadWebClient threadsUploadWebClient,
        PollingProperties pollingProperties
    ) {
        this.threadsUploadWebClient = threadsUploadWebClient;
        this.pollingProperties = pollingProperties;
    }

    public Mono<String> poll(ContainerStatusRequest request) {
        Duration delay = pollingProperties.delay();
        long delaySeconds = delay.getSeconds();
        int maxRepeat = pollingProperties.maxRepeat();

        String containerId = request.containerId();
        String accessToken = request.accessToken();

        Function<Flux<Long>, Publisher<?>> onRepeat = repeat -> repeat
            .doOnNext(repeatNum -> LOGGER.info(
                "mogq [threads-starter] Повторная попытка обработки контейнера произойдет через {} секунд ({}/{})",
                delaySeconds, repeatNum + 1, maxRepeat
            ))
            .delayElements(delay, Schedulers.boundedElastic());

        return Mono.defer(() -> validateContainerStatus(containerId, accessToken))
            .repeatWhenEmpty(maxRepeat, onRepeat);
    }

    private Mono<String> validateContainerStatus(String containerId, String accessToken) {
        return threadsUploadWebClient.getContainerStatus(containerId, accessToken)
            .map(ContainerStatusResponse::status)
            .doOnNext(status -> LOGGER.info("hnlr [threads-starter] Статус контейнера: {}", status))
            .filter("FINISHED"::equals);
    }
}
