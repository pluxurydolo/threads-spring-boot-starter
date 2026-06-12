package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class ThreadsAuthorizationCodeFlow {
    private final ThreadsAuthProperties threadsAuthProperties;

    public ThreadsAuthorizationCodeFlow(ThreadsAuthProperties threadsAuthProperties) {
        this.threadsAuthProperties = threadsAuthProperties;
    }

    public URI getAuthorizationUri() {
        String appId = threadsAuthProperties.appId();
        String redirectUri = threadsAuthProperties.redirectUri();
        String scope = "threads_basic,threads_content_publish";
        String responseType = "code";

        return UriComponentsBuilder.fromUriString("https://threads.net/oauth/authorize")
            .queryParam("client_id", appId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("scope", scope)
            .queryParam("response_type", responseType)
            .build()
            .toUri();
    }
}
