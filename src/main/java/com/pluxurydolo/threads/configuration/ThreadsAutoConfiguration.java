package com.pluxurydolo.threads.configuration;

import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import com.pluxurydolo.threads.properties.ThreadsPollingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    ThreadsAuthProperties.class,
    ThreadsPollingProperties.class
})
@Import({
    ThreadsOAuthConfiguration.class,
    ThreadsWebConfiguration.class,
    ThreadsClientConfiguration.class,
    ThreadsUploadConfiguration.class,
    ThreadsSchedulingConfiguration.class
})
public class ThreadsAutoConfiguration {
}
