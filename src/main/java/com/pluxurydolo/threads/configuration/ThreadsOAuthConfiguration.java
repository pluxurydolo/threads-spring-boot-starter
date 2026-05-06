package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.flow.oauth.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
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
    public ThreadsExchangeTokenFlow threadsExchangeTokenFlow(
        ThreadsApiHttpClient threadsApiHttpClient,
        ThreadsAuthProperties threadsAuthProperties
    ) {
        return new ThreadsExchangeTokenFlow(threadsApiHttpClient, threadsAuthProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsAccessTokenFlow threadsAccessTokenFlow(
        ThreadsAuthProperties threadsAuthProperties,
        ThreadsApiHttpClient threadsApiHttpClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new ThreadsAccessTokenFlow(threadsAuthProperties, threadsApiHttpClient, abstractTokenSaver);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsRefreshTokenFlow threadsRefreshTokenFlow(
        ThreadsApiHttpClient threadsApiHttpClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new ThreadsRefreshTokenFlow(threadsApiHttpClient, abstractTokenSaver);
    }
}
