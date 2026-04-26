package com.pluxurydolo.threads.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pluxurydolo.threads.base.AbstractIntegrationTests;
import com.pluxurydolo.threads.dto.request.upload.UploadMediaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.slf4j.LoggerFactory.getLogger;

class ThreadsVideoClientIntegrationTests extends AbstractIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(ThreadsVideoClient.class);

    @Autowired
    private ThreadsVideoClient threadsVideoClient;

    @Test
    void testUploadVideo() {
        List<ILoggingEvent> logs = listAppender().list;

        threadsVideoClient.uploadVideo(uploadMediaRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("qnoh [threads-starter] Видео успешно опубликовано");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static UploadMediaRequest uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }
}
