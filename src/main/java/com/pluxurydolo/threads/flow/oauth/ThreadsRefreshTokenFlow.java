package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.dto.ThreadsTokens;
import com.pluxurydolo.threads.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThreadsRefreshTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsRefreshTokenFlow.class);

    private final ThreadsApiHttpClient threadsApiHttpClient;
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final AbstractTokenSaver abstractTokenSaver;
    private final RefreshTokenFlowHook refreshTokenFlowHook;

    public ThreadsRefreshTokenFlow(
        ThreadsApiHttpClient threadsApiHttpClient,
        AbstractTokenRetriever abstractTokenRetriever,
        AbstractTokenSaver abstractTokenSaver,
        RefreshTokenFlowHook refreshTokenFlowHook
    ) {
        this.threadsApiHttpClient = threadsApiHttpClient;
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.abstractTokenSaver = abstractTokenSaver;
        this.refreshTokenFlowHook = refreshTokenFlowHook;
    }

    public Mono<String> refreshToken() {
        return abstractTokenRetriever.retrieve()
            .flatMap(this::updateTokens)
            .flatMap(_ -> refreshTokenFlowHook.doAfter())
            .thenReturn("SUCCESS")
            .doOnSuccess(_ -> LOGGER.info("xntj [threads-starter] Access token успешно обновлен"))
            .onErrorResume(throwable -> {
                LOGGER.error("enir [threads-starter] Произошла ошибка при обновлении access token");
                return refreshTokenFlowHook.handleException(throwable);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<String> updateTokens(ThreadsTokens threadsTokens) {
        String grantType = "th_refresh_token";
        String oldAccessToken = threadsTokens.accessToken();

        return threadsApiHttpClient.refreshToken(grantType, oldAccessToken)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, oldAccessToken));
    }
}
