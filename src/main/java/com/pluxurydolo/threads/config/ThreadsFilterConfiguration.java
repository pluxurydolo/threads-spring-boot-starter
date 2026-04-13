package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.filter.ThreadsRequestParamValidationFilter;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.validator.RequestParamValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsFilterConfiguration {

    @Bean
    public ThreadsRequestParamValidationFilter threadsRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        ThreadsProperties threadsProperties
    ) {
        return new ThreadsRequestParamValidationFilter(requestParamValidator, threadsProperties);
    }
}
