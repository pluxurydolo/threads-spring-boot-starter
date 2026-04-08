package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.validator.RequestParamValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.threads.validator.ValidationResult.SUCCESS;

@TestConfiguration
public class ValidatorTestConfig {

    @Bean
    public RequestParamValidator requestParamValidator() {
        return _ -> Mono.just(SUCCESS);
    }
}
