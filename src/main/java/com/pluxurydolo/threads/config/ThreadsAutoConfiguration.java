package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import com.pluxurydolo.threads.properties.ThreadsProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    ThreadsProperties.class,
    ThreadsPollingProperties.class
})
@Import({
    ThreadsOAuthConfiguration.class,
    ThreadsWebConfiguration.class,
    ThreadsClientConfiguration.class,
    ThreadsUploadStepConfiguration.class,
    ThreadsSchedulingConfiguration.class,
    ThreadsFilterConfiguration.class
})
public class ThreadsAutoConfiguration {
}
