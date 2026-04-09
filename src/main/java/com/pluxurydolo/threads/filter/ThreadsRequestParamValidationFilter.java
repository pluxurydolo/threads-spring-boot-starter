package com.pluxurydolo.threads.filter;

import com.pluxurydolo.threads.properties.ThreadsProperties;
import com.pluxurydolo.threads.validator.RequestParamValidator;
import com.pluxurydolo.threads.validator.ValidationResult;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.threads.validator.ValidationResult.SUCCESS;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Order(HIGHEST_PRECEDENCE)
public class ThreadsRequestParamValidationFilter implements WebFilter {
    private final RequestParamValidator requestParamValidator;
    private final ThreadsProperties threadsProperties;

    public ThreadsRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        ThreadsProperties threadsProperties
    ) {
        this.requestParamValidator = requestParamValidator;
        this.threadsProperties = threadsProperties;
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

        String accessToken = request.getQueryParams().getFirst("access_token");

        return requestParamValidator.validate(accessToken)
            .flatMap(result -> handleValidationResult(exchange, chain, result));
    }

    private static Mono<Void> handleValidationResult(
        ServerWebExchange serverWebExchange,
        WebFilterChain webFilterChain,
        ValidationResult validationResult
    ) {
        if (validationResult == SUCCESS) {
            return webFilterChain.filter(serverWebExchange);
        }

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(FORBIDDEN);
        return response.setComplete();
    }
}
