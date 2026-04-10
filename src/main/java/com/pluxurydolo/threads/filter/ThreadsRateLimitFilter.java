package com.pluxurydolo.threads.filter;

import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.properties.ThreadsRateLimitProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

public class ThreadsRateLimitFilter implements WebFilter {
    private final AtomicInteger requestCounter;
    private final ThreadsProperties threadsProperties;
    private final ThreadsRateLimitProperties threadsRateLimitProperties;

    public ThreadsRateLimitFilter(
        AtomicInteger requestCounter,
        ThreadsProperties threadsProperties,
        ThreadsRateLimitProperties threadsRateLimitProperties
    ) {
        this.requestCounter = requestCounter;
        this.threadsProperties = threadsProperties;
        this.threadsRateLimitProperties = threadsRateLimitProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String loginUrl = threadsProperties.loginUrl();
        String redirectUrl = threadsProperties.redirectUrl();
        String refreshUrl = threadsProperties.refreshUrl();

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (!path.equals(loginUrl) && !path.equals(redirectUrl) && !path.equals(refreshUrl)) {
            return chain.filter(exchange);
        }

        int threshold = threadsRateLimitProperties.threshold();

        if (requestCounter.incrementAndGet() <= threshold) {
            return handleRequest(exchange, chain);
        } else {
            return dropRequest(exchange);
        }
    }

    private Mono<Void> handleRequest(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
            .doFinally(_ -> requestCounter.decrementAndGet());
    }

    private Mono<Void> dropRequest(ServerWebExchange exchange) {
        requestCounter.decrementAndGet();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(TOO_MANY_REQUESTS);

        return response.setComplete();
    }
}
