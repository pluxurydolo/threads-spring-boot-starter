package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.controller.ThreadsOAuthController;
import com.pluxurydolo.threads.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
import com.pluxurydolo.threads.service.ThreadsOAuthService;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsWebConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsOAuthController threadsOAuthController(ThreadsOAuthService threadsOAuthService) {
        return new ThreadsOAuthController(threadsOAuthService);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsOAuthService threadsOAuthService(
        ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow,
        ThreadsExchangeTokenFlow threadsExchangeTokenFlow,
        ThreadsAccessTokenFlow threadsAccessTokenFlow,
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever
    ) {
        return new ThreadsOAuthService(
            threadsAuthorizationCodeFlow,
            threadsExchangeTokenFlow,
            threadsAccessTokenFlow,
            threadsRefreshTokenFlow,
            abstractTokenRetriever
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsApiWebClient threadsApiWebClient() {
        return new ThreadsApiWebClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsUploadWebClient threadsUploadWebClient() {
        return new ThreadsUploadWebClient();
    }
}
