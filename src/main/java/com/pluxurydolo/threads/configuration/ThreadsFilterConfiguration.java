package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.filter.ThreadsRequestParamValidationFilter;
import com.pluxurydolo.threads.properties.ThreadsEndpointProperties;
import com.pluxurydolo.threads.validator.RequestParamValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadsFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadsRequestParamValidationFilter threadsRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        ThreadsEndpointProperties threadsEndpointProperties
    ) {
        return new ThreadsRequestParamValidationFilter(requestParamValidator, threadsEndpointProperties);
    }
}
