package com.pluxurydolo.threads.controller;

import com.pluxurydolo.threads.service.ThreadsOAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.resilience.annotation.ConcurrencyLimit.ThrottlePolicy.REJECT;

@RestController
public class ThreadsOAuthController {
    private final ThreadsOAuthService threadsOAuthService;

    public ThreadsOAuthController(ThreadsOAuthService threadsOAuthService) {
        this.threadsOAuthService = threadsOAuthService;
    }

    @GetMapping("${threads.login.url}")
    @ConcurrencyLimit(limit = 5, policy = REJECT)
    public Mono<ResponseEntity<Void>> login() {
        return threadsOAuthService.login();
    }

    @GetMapping("${threads.redirect.url}")
    @ConcurrencyLimit(limit = 5, policy = REJECT)
    public Mono<String> callback(@RequestParam("code") String code) {
        return threadsOAuthService.callback(code);
    }

    @GetMapping("${threads.refresh.url}")
    @ConcurrencyLimit(limit = 5, policy = REJECT)
    public Mono<String> refreshToken() {
        return threadsOAuthService.refreshToken();
    }
}
