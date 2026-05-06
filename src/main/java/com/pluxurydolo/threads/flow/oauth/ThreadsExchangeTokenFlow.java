package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.exception.ThreadsExchangeTokenFlowException;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsExchangeTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsExchangeTokenFlow.class);

    private final ThreadsApiHttpClient threadsApiHttpClient;
    private final ThreadsAuthProperties threadsAuthProperties;

    public ThreadsExchangeTokenFlow(ThreadsApiHttpClient threadsApiHttpClient, ThreadsAuthProperties threadsAuthProperties) {
        this.threadsApiHttpClient = threadsApiHttpClient;
        this.threadsAuthProperties = threadsAuthProperties;
    }

    public Mono<TokenResponse> getToken(String code) {
        String appId = threadsAuthProperties.appId();
        String appSecret = threadsAuthProperties.appSecret();
        String redirectUri = threadsAuthProperties.redirectUri();
        String grantType = "authorization_code";

        return threadsApiHttpClient.getExchangeToken(appId, appSecret, redirectUri, code, grantType)
            .doOnSuccess(_ -> LOGGER.info("yung [threads-starter] Exchange token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("gknh [threads-starter] Произошла ошибка при получении exchange token");
                return Mono.error(new ThreadsExchangeTokenFlowException(throwable));
            });
    }
}
