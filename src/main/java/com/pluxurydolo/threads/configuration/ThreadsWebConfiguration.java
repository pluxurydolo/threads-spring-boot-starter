package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.controller.ThreadsOAuthController;
import com.pluxurydolo.threads.flow.oauth.ThreadsAccessTokenFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsAuthorizationCodeFlow;
import com.pluxurydolo.threads.flow.oauth.ThreadsRefreshTokenFlow;
import com.pluxurydolo.threads.service.ThreadsOAuthService;
import com.pluxurydolo.threads.web.ThreadsApiHttpClient;
import com.pluxurydolo.threads.web.ThreadsUploadHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.function.Consumer;

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
        ThreadsAccessTokenFlow threadsAccessTokenFlow,
        ThreadsRefreshTokenFlow threadsRefreshTokenFlow
    ) {
        return new ThreadsOAuthService(threadsAuthorizationCodeFlow, threadsAccessTokenFlow, threadsRefreshTokenFlow);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsApiHttpClient threadsApiHttpClient() {
        WebClient webClient = WebClient.builder()
            .build();

        WebClientAdapter exchangeAdapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builderFor(exchangeAdapter)
            .build()
            .createClient(ThreadsApiHttpClient.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadsUploadHttpClient threadsUploadHttpClient() {
        Consumer<ClientCodecConfigurer> codec = configurer -> configurer
            .defaultCodecs()
            .maxInMemorySize(16 * 1024 * 1024);

        WebClient webClient = WebClient.builder()
            .codecs(codec)
            .build();

        WebClientAdapter exchangeAdapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builderFor(exchangeAdapter)
            .build()
            .createClient(ThreadsUploadHttpClient.class);
    }
}
