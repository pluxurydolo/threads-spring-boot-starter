package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.flow.oauth.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.flow.oauth.hook.AccessTokenFlowHook;
import com.pluxurydolo.threads.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsOAuthConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow(ThreadsAuthProperties threadsAuthProperties) {
        return new ThreadsAuthorizationCodeFlow(threadsAuthProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsAccessTokenFlow threadsAccessTokenFlow(
        ThreadsAuthProperties threadsAuthProperties,
        ThreadsApiHttpClient threadsApiHttpClient,
        AbstractTokenSaver abstractTokenSaver,
        AccessTokenFlowHook accessTokenFlowHook
    ) {
        return new ThreadsAccessTokenFlow(
            threadsAuthProperties,
            threadsApiHttpClient,
            abstractTokenSaver,
            accessTokenFlowHook
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsRefreshTokenFlow threadsRefreshTokenFlow(
        ThreadsApiHttpClient threadsApiHttpClient,
        AbstractTokenRetriever abstractTokenRetriever,
        AbstractTokenSaver abstractTokenSaver,
        RefreshTokenFlowHook refreshTokenFlowHook
    ) {
        return new ThreadsRefreshTokenFlow(
            threadsApiHttpClient,
            abstractTokenRetriever,
            abstractTokenSaver,
            refreshTokenFlowHook
        );
    }
}
