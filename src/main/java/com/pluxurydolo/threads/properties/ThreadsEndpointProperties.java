package com.pluxurydolo.threads.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "threads.endpoint")
public record ThreadsEndpointProperties(

    @Name("login")
    String loginUrl,

    @Name("redirect")
    String redirectUrl,

    @Name("refresh-token")
    String refreshTokenUrl
) {
}
