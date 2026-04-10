package com.pluxurydolo.threads.filter;

import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.properties.ThreadsRateLimitProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ThreadsRateLimitFilterTests {

    @Mock
    private AtomicInteger requestCounter;

    @Mock
    private ThreadsProperties threadsProperties;

    @Mock
    private ThreadsRateLimitProperties threadsRateLimitProperties;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private WebFilterChain webFilterChain;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock
    private URI uri;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @InjectMocks
    private ThreadsRateLimitFilter threadsRateLimitFilter;

    @BeforeEach
    void setUp() {
        when(threadsProperties.loginUrl())
            .thenReturn("loginUrl");
        when(threadsProperties.redirectUrl())
            .thenReturn("redirectUrl");
        when(threadsProperties.refreshUrl())
            .thenReturn("refreshUrl");
        when(serverWebExchange.getRequest())
            .thenReturn(serverHttpRequest);
        when(serverHttpRequest.getURI())
            .thenReturn(uri);
    }

    @Test
    void testFilterWithLoginPath() {
        when(uri.getPath())
            .thenReturn("loginUrl");
        when(threadsRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(5);
        when(requestCounter.decrementAndGet())
            .thenReturn(4);
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithLoginPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("loginUrl");
        when(threadsRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(6);
        when(requestCounter.decrementAndGet())
            .thenReturn(5);
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRedirectPath() {
        when(uri.getPath())
            .thenReturn("redirectUrl");
        when(threadsRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(5);
        when(requestCounter.decrementAndGet())
            .thenReturn(4);
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRedirectPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("redirectUrl");
        when(threadsRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(6);
        when(requestCounter.decrementAndGet())
            .thenReturn(5);
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRefreshPath() {
        when(uri.getPath())
            .thenReturn("refreshUrl");
        when(threadsRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(5);
        when(requestCounter.decrementAndGet())
            .thenReturn(4);
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRefreshPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("refreshUrl");
        when(threadsRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(6);
        when(requestCounter.decrementAndGet())
            .thenReturn(5);
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterOtherPath() {
        when(uri.getPath())
            .thenReturn("other");
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = threadsRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }
}
