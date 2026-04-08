package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.controller.ThreadsOAuthController;
import com.pluxurydolo.threads.flow.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.ThreadsExchangeTokenFlow;
import com.pluxurydolo.threads.flow.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.token.AbstractTokenRetriever;
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
    public ThreadsApiWebClient threadsApiWebClient() {
        return new ThreadsApiWebClient();
    }

    @Bean
    public ThreadsUploadWebClient threadsUploadWebClient() {
        return new ThreadsUploadWebClient();
    }
}
