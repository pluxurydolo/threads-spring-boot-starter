package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.controller.ThreadsOAuthController;
import com.pluxurydolo.threads.security.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.security.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.security.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.security.token.AbstractTokensRetriever;
import com.pluxurydolo.threads.service.ThreadsOAuthService;
import com.pluxurydolo.threads.web.ThreadsApiWebClient;
import com.pluxurydolo.threads.web.ThreadsUploadWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsWebConfiguration {

    @Bean
    public ThreadsOAuthController threadsOAuthController(ThreadsOAuthService threadsOAuthService) {
        return new ThreadsOAuthController(threadsOAuthService);
    }

    @Bean
    public ThreadsOAuthService threadsOAuthService(
        ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow,
        ThreadsExchangeTokenFlow threadsExchangeTokenFlow,
        ThreadsAccessTokenFlow threadsAccessTokenFlow,
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow,
        AbstractTokensRetriever abstractTokensRetriever
    ) {
        return new ThreadsOAuthService(
            threadsAuthorizationCodeFlow,
            threadsExchangeTokenFlow,
            threadsAccessTokenFlow,
            threadsRefreshTokenFlow,
            abstractTokensRetriever
        );
    }

    @Bean
    public ThreadsApiWebClient threadsApiWebClient() {
        return new ThreadsApiWebClient();
    }

    @Bean
    public ThreadsUploadWebClient threadsUploadWebClient() {
        return new ThreadsUploadWebClient();
    }
}
