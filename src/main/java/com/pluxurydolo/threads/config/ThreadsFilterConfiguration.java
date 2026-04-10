package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.filter.ThreadsRateLimitFilter;
import com.pluxurydolo.threads.filter.ThreadsRequestParamValidationFilter;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.properties.ThreadsRateLimitProperties;
import com.pluxurydolo.threads.validator.RequestParamValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ThreadsFilterConfiguration {

    @Bean
    public ThreadsRequestParamValidationFilter threadsRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        ThreadsProperties threadsProperties
    ) {
        return new ThreadsRequestParamValidationFilter(requestParamValidator, threadsProperties);
    }

    @Bean
    public ThreadsRateLimitFilter threadsRateLimitingFilter(
        AtomicInteger requestCounter,
        ThreadsProperties threadsProperties,
        ThreadsRateLimitProperties threadsRateLimitProperties
    ) {
        return new ThreadsRateLimitFilter(requestCounter, threadsProperties, threadsRateLimitProperties);
    }

    @Bean
    public AtomicInteger requestCounter() {
        return new AtomicInteger(0);
    }
}
