package com.pluxurydolo.threads.web;

import com.pluxurydolo.threads.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.threads.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.threads.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import com.pluxurydolo.threads.exception.ThreadsCreateImageContainerException;
import com.pluxurydolo.threads.exception.ThreadsImageContainerStatusException;
import com.pluxurydolo.threads.exception.ThreadsPublishImageContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

public class ThreadsUploadWebClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsUploadWebClient.class);

    private final WebClient webClient;

    public ThreadsUploadWebClient() {
        this.webClient = WebClient.builder()
            .baseUrl("https://graph.threads.net")
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
            .build();
    }

    public Mono<CreateContainerResponse> createImageContainer(CreateContainerRequest request) {
        String imageUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();

        BodyInserters.FormInserter<String> body = fromFormData("media_type", "IMAGE")
            .with("image_url", imageUrl)
            .with("access_token", accessToken)
            .with("text", caption);

        return webClient.post()
            .uri("/v1.0/{userId}/threads", userId)
            .body(body)
            .retrieve()
            .bodyToMono(CreateContainerResponse.class)
            .doOnSuccess(_ -> LOGGER.info("ilau [threads-starter] Контейнер изображения {} успешно создан", imageUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("yjwz [threads-starter] Произошла ошибка при создании контейнера изображения {}", imageUrl);
                return Mono.error(new ThreadsCreateImageContainerException(throwable));
            });
    }

    public Mono<CreateContainerResponse> createVideoContainer(CreateContainerRequest request) {
        String videoUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();

        BodyInserters.FormInserter<String> body = fromFormData("media_type", "VIDEO")
            .with("video_url", videoUrl)
            .with("access_token", accessToken)
            .with("text", caption);

        return webClient.post()
            .uri("/v1.0/{userId}/threads", userId)
            .contentType(APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .bodyToMono(CreateContainerResponse.class)
            .doOnSuccess(_ -> LOGGER.info("rnud [threads-starter] Контейнер видео {} успешно создан", videoUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("sjgf [threads-starter] Произошла ошибка при создании контейнера видео {}", videoUrl);
                return Mono.error(new ThreadsCreateImageContainerException(throwable));
            });
    }

    public Mono<PublishContainerResponse> publishContainer(PublishContainerRequest request) {
        String containerId = request.containerId();
        String userId = request.userId();
        String accessToken = request.accessToken();

        BodyInserters.FormInserter<String> body = fromFormData("creation_id", containerId)
            .with("access_token", accessToken);

        return webClient.post()
            .uri("/v1.0/{userId}/threads_publish", userId)
            .body(body)
            .retrieve()
            .bodyToMono(PublishContainerResponse.class)
            .doOnSuccess(_ -> LOGGER.info("dohz [threads-starter] Контейнер {} успешно опубликован", containerId))
            .onErrorResume(throwable -> {
                LOGGER.error("xrqr [threads-starter] Произошла ошибка при публикации контейнера {}", containerId);
                return Mono.error(new ThreadsPublishImageContainerException(throwable));
            });
    }

    public Mono<ContainerStatusResponse> getContainerStatus(ContainerStatusRequest request) {
        String containerId = request.containerId();
        String accessToken = request.accessToken();

        Function<UriBuilder, URI> uri = uriBuilder -> uriBuilder
            .path("/v1.0/{containerId}")
            .queryParam("fields", "status,error_message")
            .queryParam("access_token", accessToken)
            .build(containerId);

        return webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(ContainerStatusResponse.class)
            .doOnSuccess(_ -> LOGGER.info("vzfm [threads-starter] Статус контейнера {} успешно получен", containerId))
            .onErrorResume(throwable -> {
                LOGGER.error("tyrx [threads-starter] Произошла ошибка при проверке статуса контейнера {}", containerId);
                return Mono.error(new ThreadsImageContainerStatusException(throwable));
            });
    }
}
