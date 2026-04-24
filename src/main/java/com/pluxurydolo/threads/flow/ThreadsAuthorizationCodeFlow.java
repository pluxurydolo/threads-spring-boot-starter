package com.pluxurydolo.threads.flow;

import com.pluxurydolo.threads.properties.ThreadsAuthProperties;

import static java.lang.String.format;

public class ThreadsAuthorizationCodeFlow {
    private final ThreadsAuthProperties threadsAuthProperties;

    public ThreadsAuthorizationCodeFlow(ThreadsAuthProperties threadsAuthProperties) {
        this.threadsAuthProperties = threadsAuthProperties;
    }

    public String getAuthorizationUrl() {
        String appId = threadsAuthProperties.appId();
        String redirectUri = threadsAuthProperties.redirectUri();
        String scope = "threads_basic,threads_content_publish";
        String responseType = "code";

        return format(
            "https://threads.net/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s&response_type=%s",
            appId, redirectUri, scope, responseType
        );
    }
}
