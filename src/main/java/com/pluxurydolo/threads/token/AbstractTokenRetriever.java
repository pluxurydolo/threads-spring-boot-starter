package com.pluxurydolo.threads.token;

import com.pluxurydolo.threads.dto.ThreadsTokens;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractTokenRetriever {
    public Mono<ThreadsTokens> retrieve() {
        return retrieveTokens()
            .map(AbstractTokenRetriever::mapToTokens);
    }

    private static ThreadsTokens mapToTokens(Map<String, String> tokens) {
        String accessToken = tokens.get("access_token");
        String exchangeToken = tokens.get("exchange_token");
        return new ThreadsTokens(exchangeToken, accessToken);
    }

    protected abstract Mono<Map<String, String>> retrieveTokens();
}
