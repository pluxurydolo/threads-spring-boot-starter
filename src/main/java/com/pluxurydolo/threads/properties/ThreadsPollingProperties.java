package com.pluxurydolo.threads.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

import java.time.Duration;

@ConfigurationProperties(prefix = "threads.polling")
public record ThreadsPollingProperties(

    @Name("max-repeat")
    int maxRepeat,

    @Name("delay")
    Duration delay
) {
}
