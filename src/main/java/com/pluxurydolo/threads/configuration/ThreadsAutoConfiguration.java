package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.properties.ThreadsEndpointProperties;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    ThreadsAuthProperties.class,
    ThreadsEndpointProperties.class,
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
