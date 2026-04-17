package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsOAuthConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow(ThreadsProperties threadsProperties) {
        return new ThreadsAuthorizationCodeFlow(threadsProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsExchangeTokenFlow threadsExchangeTokenFlow(
        ThreadsApiWebClient threadsApiWebClient,
        ThreadsProperties threadsProperties
    ) {
        return new ThreadsExchangeTokenFlow(threadsApiWebClient, threadsProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsAccessTokenFlow threadsAccessTokenFlow(
        ThreadsProperties threadsProperties,
        ThreadsApiWebClient threadsApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new ThreadsAccessTokenFlow(threadsProperties, threadsApiWebClient, abstractTokenSaver);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsRefreshTokenFlow threadsRefreshTokenFlow(
        ThreadsApiWebClient threadsApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new ThreadsRefreshTokenFlow(threadsApiWebClient, abstractTokenSaver);
    }
}
