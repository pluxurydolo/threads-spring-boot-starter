package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.exception.ThreadsAccessTokenFlowException;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsAccessTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsAccessTokenFlow.class);

    private final ThreadsAuthProperties threadsAuthProperties;
    private final ThreadsApiHttpClient threadsApiHttpClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public ThreadsAccessTokenFlow(
        ThreadsAuthProperties threadsAuthProperties,
        ThreadsApiHttpClient threadsApiHttpClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.threadsAuthProperties = threadsAuthProperties;
        this.threadsApiHttpClient = threadsApiHttpClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appSecret = threadsAuthProperties.appSecret();
        String grantType = "th_exchange_token";

        return threadsApiHttpClient.getAccessToken(grantType, appSecret, exchangeToken)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, exchangeToken))
            .doOnSuccess(_ -> LOGGER.info("spus [threads-starter] Access token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("xrqc [threads-starter] Произошла ошибка при получении access token");
                return Mono.error(new ThreadsAccessTokenFlowException(throwable));
            });
    }
}
