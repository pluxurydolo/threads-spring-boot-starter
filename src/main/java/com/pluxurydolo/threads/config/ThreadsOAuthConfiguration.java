package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.security.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.security.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.security.token.AbstractTokensSaver;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsOAuthConfiguration {

    @Bean
    public ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow(ThreadsProperties threadsProperties) {
        return new ThreadsAuthorizationCodeFlow(threadsProperties);
    }

    @Bean
    public ThreadsExchangeTokenFlow threadsExchangeTokenFlow(
        ThreadsApiWebClient threadsApiWebClient,
        ThreadsProperties threadsProperties
    ) {
        return new ThreadsExchangeTokenFlow(threadsApiWebClient, threadsProperties);
    }

    @Bean
    public ThreadsAccessTokenFlow threadsAccessTokenFlow(
        ThreadsProperties threadsProperties,
        ThreadsApiWebClient threadsApiWebClient,
        AbstractTokensSaver abstractTokensSaver
    ) {
        return new ThreadsAccessTokenFlow(threadsProperties, threadsApiWebClient, abstractTokensSaver);
    }

    @Bean
    public ThreadsRefreshTokenFlow threadsRefreshTokenFlow(ThreadsAccessTokenFlow threadsAccessTokenFlow) {
        return new ThreadsRefreshTokenFlow(threadsAccessTokenFlow);
    }
}
