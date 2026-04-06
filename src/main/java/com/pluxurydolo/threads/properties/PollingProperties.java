package com.pluxurydolo.threads.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "threads.polling")
public record PollingProperties(
    int maxRepeat,
    Duration delay
) {
}
