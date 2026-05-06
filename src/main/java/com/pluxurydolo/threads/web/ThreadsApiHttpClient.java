package com.pluxurydolo.threads.web;

import com.pluxurydolo.threads.dto.response.TokenResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange(url = "https://graph.threads.net")
public interface ThreadsApiHttpClient {

    @GetExchange("/oauth/access_token")
    Mono<TokenResponse> getExchangeToken(
        @RequestParam("client_id") String appId,
        @RequestParam("client_secret") String appSecret,
        @RequestParam("redirect_uri") String redirectUri,
        @RequestParam("code") String code,
        @RequestParam("grant_type") String grantType
    );

    @GetExchange("/access_token")
    Mono<TokenResponse> getAccessToken(
        @RequestParam("grant_type") String grantType,
        @RequestParam("client_secret") String appSecret,
        @RequestParam("access_token") String exchangeToken
    );

    @GetExchange("/refresh_access_token")
    Mono<TokenResponse> refreshToken(
        @RequestParam("grant_type") String grantType,
        @RequestParam("access_token") String accessToken
    );
}
