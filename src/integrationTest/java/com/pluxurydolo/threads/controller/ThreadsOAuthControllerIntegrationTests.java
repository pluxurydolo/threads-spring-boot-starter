package com.pluxurydolo.threads.controller;

import com.pluxurydolo.threads.base.AbstractControllerIntegrationTests;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadsOAuthControllerIntegrationTests extends AbstractControllerIntegrationTests {

    @Test
    void testLogin() {
        webTestClient.get()
            .uri("/app-name/v1/threads/login")
            .exchange()
            .expectStatus().isFound()
            .expectHeader().location(locationHeader())
            .expectBody().isEmpty();
    }

    @Test
    void testRedirect() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/app-name/v1/threads/login/redirect")
                .queryParam("code", "code")
                .build())
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(response -> assertThat(response).isEqualTo("saveTokens"));
    }

    @Test
    void testRefresh() {
        webTestClient.get()
            .uri("/app-name/v1/threads/refresh")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(response -> assertThat(response).isEqualTo("saveTokens"));
    }

    private static String locationHeader() {
        return "https://threads.net/oauth/authorize?client_id=id&redirect_uri=http://localhost:8888$/app-name/v1/threads/login/redirect&scope=threads_basic,threads_content_publish&response_type=code";
    }
}
