package com.pluxurydolo.threads.web;

import com.pluxurydolo.threads.dto.request.security.AccessTokenRequest;
import com.pluxurydolo.threads.dto.request.security.ExchangeTokenRequest;
import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.exception.ThreadsAccessTokenFlowException;
import com.pluxurydolo.threads.exception.ThreadsExchangeTokenFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ThreadsApiWebClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsApiWebClient.class);

    private final WebClient webClient;

    public ThreadsApiWebClient() {
        this.webClient = WebClient.builder()
            .baseUrl("https://graph.threads.net")
            .build();
    }

    public Mono<TokenResponse> getExchangeToken(ExchangeTokenRequest request) {
        String appId = request.appId();
        String appSecret = request.appSecret();
        String redirectUri = request.redirectUri();
        String code = request.code();

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/oauth/access_token")
                .queryParam("client_id", appId)
                .queryParam("client_secret", appSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .build()
            )
            .retrieve()
            .bodyToMono(TokenResponse.class)
            .doOnSuccess(_ -> LOGGER.info("yung [threads-starter] Exchange token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("gknh [threads-starter] Произошла ошибка при получении exchange token");
                return Mono.error(new ThreadsExchangeTokenFlowException(throwable));
            });
    }

    public Mono<TokenResponse> getAccessToken(AccessTokenRequest request) {
        String appSecret = request.appSecret();
        String exchangeToken = request.exchangeToken();

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/access_token")
                .queryParam("grant_type", "th_exchange_token")
                .queryParam("client_secret", appSecret)
                .queryParam("access_token", exchangeToken)
                .build()
            )
            .retrieve()
            .bodyToMono(TokenResponse.class)
            .doOnSuccess(_ -> LOGGER.info("spus [threads-starter] Access token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("xrqc [threads-starter] Произошла ошибка при получении access token");
                return Mono.error(new ThreadsAccessTokenFlowException(throwable));
            });
    }
}
