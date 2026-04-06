package com.pluxurydolo.threads.security.token;

import com.pluxurydolo.threads.dto.Tokens;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractTokensRetriever {
    public Mono<Tokens> retrieve() {
        return retrieveTokens()
            .map(AbstractTokensRetriever::mapToTokens);
    }

    private static Tokens mapToTokens(Map<String, String> tokens) {
        String accessToken = tokens.get("access_token");
        String exchangeToken = tokens.get("exchange_token");
        return new Tokens(exchangeToken, accessToken);
    }

    protected abstract Mono<Map<String, String>> retrieveTokens();
}
