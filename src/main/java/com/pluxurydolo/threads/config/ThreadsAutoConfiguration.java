package com.pluxurydolo.threads.config;

import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnProperty(prefix = "threads", name = "enabled", havingValue = "true")
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
