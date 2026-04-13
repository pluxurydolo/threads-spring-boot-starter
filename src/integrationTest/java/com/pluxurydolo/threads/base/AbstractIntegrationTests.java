package com.pluxurydolo.threads.base;

import com.pluxurydolo.threads.TestApplication;
import com.pluxurydolo.threads.config.SchedulerTestConfig;
import com.pluxurydolo.threads.config.TokensTestConfig;
import com.pluxurydolo.threads.config.ValidatorTestConfig;
import com.pluxurydolo.threads.config.WebTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    TokensTestConfig.class,
    ValidatorTestConfig.class,
    SchedulerTestConfig.class,
    WebTestConfig.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
    "threads.enabled=true",
    "threads.app.id=id",
    "threads.app.secret=secret",
    "threads.user.id=id",
    "threads.login.url=/app-name/v1/threads/login",
    "threads.redirect.uri=http://localhost:8888$/app-name/v1/threads/login/redirect",
    "threads.redirect.url=/app-name/v1/threads/login/redirect",
    "threads.refresh.url=/app-name/v1/threads/refresh",
    "threads.refresh.token.scheduler.cron=0 0 0 * * SUN",
    "threads.refresh.token.scheduler.zone=Europe/Moscow",
    "threads.polling.max-repeat=20",
    "threads.polling.delay=10s"
})
public abstract class AbstractIntegrationTests {
}
