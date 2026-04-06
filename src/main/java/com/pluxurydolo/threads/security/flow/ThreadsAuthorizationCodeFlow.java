package com.pluxurydolo.threads.security.flow;

import com.pluxurydolo.threads.properties.ThreadsProperties;

import static java.lang.String.format;

public class ThreadsAuthorizationCodeFlow {
    private final ThreadsProperties threadsProperties;

    public ThreadsAuthorizationCodeFlow(ThreadsProperties threadsProperties) {
        this.threadsProperties = threadsProperties;
    }

    public String getAuthorizationUrl() {
        String appId = threadsProperties.appId();
        String redirectUri = threadsProperties.redirectUri();
        String scope = "threads_basic,threads_content_publish";
        String responseType = "code";

        return format(
            "https://threads.net/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s&response_type=%s",
            appId, redirectUri, scope, responseType
        );
    }
}
