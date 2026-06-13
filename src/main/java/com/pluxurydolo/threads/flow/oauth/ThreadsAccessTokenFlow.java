package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import com.pluxurydolo.threads.flow.oauth.hook.AccessTokenFlowHook;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThreadsAccessTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsAccessTokenFlow.class);

    private final ThreadsAuthProperties threadsAuthProperties;
    private final ThreadsApiHttpClient threadsApiHttpClient;
    private final AbstractTokenSaver abstractTokenSaver;
    private final AccessTokenFlowHook accessTokenFlowHook;

    public ThreadsAccessTokenFlow(
        ThreadsAuthProperties threadsAuthProperties,
        ThreadsApiHttpClient threadsApiHttpClient,
        AbstractTokenSaver abstractTokenSaver,
        AccessTokenFlowHook accessTokenFlowHook
    ) {
        this.threadsAuthProperties = threadsAuthProperties;
        this.threadsApiHttpClient = threadsApiHttpClient;
        this.abstractTokenSaver = abstractTokenSaver;
        this.accessTokenFlowHook = accessTokenFlowHook;
    }

    public Mono<String> getAccessToken(String code) {
        String appId = threadsAuthProperties.appId();
        String appSecret = threadsAuthProperties.appSecret();
        String redirectUri = threadsAuthProperties.redirectUri();
        String grantType = "authorization_code";

        return threadsApiHttpClient.getExchangeToken(appId, appSecret, redirectUri, code, grantType)
            .flatMap(this::updateTokens)
            .flatMap(_ -> accessTokenFlowHook.doAfter())
            .thenReturn("SUCCESS")
            .doOnSuccess(_ -> LOGGER.info("spus [threads-starter] Access token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("xrqc [threads-starter] Произошла ошибка при получении access token");
                return accessTokenFlowHook.handleException(throwable);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<String> updateTokens(TokenResponse tokenResponse) {
        String appSecret = threadsAuthProperties.appSecret();
        String grantType = "th_exchange_token";

        String exchangeToken = tokenResponse.accessToken();

        return threadsApiHttpClient.getAccessToken(grantType, appSecret, exchangeToken)
            .flatMap(response -> abstractTokenSaver.save(response, exchangeToken));
    }
}
