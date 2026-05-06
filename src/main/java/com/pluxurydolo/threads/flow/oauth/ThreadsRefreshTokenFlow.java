package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.exception.ThreadsRefreshTokenFlowException;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ThreadsRefreshTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsRefreshTokenFlow.class);

    private final ThreadsApiHttpClient threadsApiHttpClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public ThreadsRefreshTokenFlow(ThreadsApiHttpClient threadsApiHttpClient, AbstractTokenSaver abstractTokenSaver) {
        this.threadsApiHttpClient = threadsApiHttpClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> refreshToken(String accessToken) {
        String grantType = "th_refresh_token";

        return threadsApiHttpClient.refreshToken(grantType, accessToken)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, accessToken))
            .doOnSuccess(_ -> LOGGER.info("xntj [threads-starter] Access token успешно обновлен"))
            .onErrorResume(throwable -> {
                LOGGER.error("enir [threads-starter] Произошла ошибка при обновлении access token");
                return Mono.error(new ThreadsRefreshTokenFlowException(throwable));
            });
    }
}
