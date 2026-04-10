package com.pluxurydolo.threads.controller;

import com.pluxurydolo.threads.service.ThreadsOAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class ThreadsOAuthController {
    private final ThreadsOAuthService threadsOAuthService;

    public ThreadsOAuthController(ThreadsOAuthService threadsOAuthService) {
        this.threadsOAuthService = threadsOAuthService;
    }

    @GetMapping("${threads.login.url}")
    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        return threadsOAuthService.login(serverWebExchange);
    }

    @GetMapping("${threads.redirect.url}")
    public Mono<String> callback(@RequestParam("code") String code) {
        return threadsOAuthService.callback(code);
    }

    @GetMapping("${threads.refresh.url}")
    public Mono<String> refreshToken() {
        return threadsOAuthService.refreshToken();
    }
}
