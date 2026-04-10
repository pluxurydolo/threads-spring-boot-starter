package com.pluxurydolo.threads.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "threads.rate-limit")
public record ThreadsRateLimitProperties(int threshold) {
}
