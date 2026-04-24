package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
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
    public ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow(ThreadsAuthProperties threadsAuthProperties) {
        return new ThreadsAuthorizationCodeFlow(threadsAuthProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsExchangeTokenFlow threadsExchangeTokenFlow(
        ThreadsApiWebClient threadsApiWebClient,
        ThreadsAuthProperties threadsAuthProperties
    ) {
        return new ThreadsExchangeTokenFlow(threadsApiWebClient, threadsAuthProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsAccessTokenFlow threadsAccessTokenFlow(
        ThreadsAuthProperties threadsAuthProperties,
        ThreadsApiWebClient threadsApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new ThreadsAccessTokenFlow(threadsAuthProperties, threadsApiWebClient, abstractTokenSaver);
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
