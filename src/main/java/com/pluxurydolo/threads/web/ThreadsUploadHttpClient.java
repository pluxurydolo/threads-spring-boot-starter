package com.pluxurydolo.threads.web;

import com.pluxurydolo.threads.dto.response.ContainerStatusResponse;
import com.pluxurydolo.threads.dto.response.CreateContainerResponse;
import com.pluxurydolo.threads.dto.response.PublishContainerResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@HttpExchange(url = "https://graph.threads.net")
public interface ThreadsUploadHttpClient {

    @PostExchange(
        url = "/v1.0/{userId}/threads",
        contentType = APPLICATION_FORM_URLENCODED_VALUE
    )
    Mono<CreateContainerResponse> createImageContainer(
        @PathVariable String userId,
        @RequestParam("media_type") String mediaType,
        @RequestParam("image_url") String imageUrl,
        @RequestParam("access_token") String accessToken,
        @RequestParam("text") String caption
    );

    @PostExchange(
        url = "/v1.0/{userId}/threads",
        contentType = APPLICATION_FORM_URLENCODED_VALUE
    )
    Mono<CreateContainerResponse> createVideoContainer(
        @PathVariable String userId,
        @RequestParam("media_type") String mediaType,
        @RequestParam("video_url") String videoUrl,
        @RequestParam("access_token") String accessToken,
        @RequestParam("text") String caption
    );

    @PostExchange(
        url = "/v1.0/{userId}/threads_publish",
        contentType = APPLICATION_FORM_URLENCODED_VALUE
    )
    Mono<PublishContainerResponse> publishContainer(
        @PathVariable String userId,
        @RequestParam("creation_id") String containerId,
        @RequestParam("access_token") String accessToken
    );

    @GetExchange("/v1.0/{containerId}")
    Mono<ContainerStatusResponse> getContainerStatus(
        @PathVariable String containerId,
        @RequestParam("fields") String fields,
        @RequestParam("access_token") String accessToken
    );
}
