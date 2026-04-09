package com.pluxurydolo.threads.step;

import com.pluxurydolo.threads.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
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
    private final ThreadsPollingProperties threadsPollingProperties;

    public ThreadsContainerStatusPoller(
        ThreadsUploadWebClient threadsUploadWebClient,
        ThreadsPollingProperties threadsPollingProperties
    ) {
        this.threadsUploadWebClient = threadsUploadWebClient;
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
        return threadsUploadWebClient.getContainerStatus(request)
            .map(ContainerStatusResponse::status)
            .doOnNext(status -> LOGGER.info("hnlr [threads-starter] Статус контейнера: {}", status))
            .filter("FINISHED"::equals);
    }
}
