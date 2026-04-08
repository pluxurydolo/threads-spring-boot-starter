package com.pluxurydolo.threads.base;

import com.pluxurydolo.threads.TestApplication;
import com.pluxurydolo.threads.config.SchedulerTestConfig;
import com.pluxurydolo.threads.config.TokensTestConfig;
import com.pluxurydolo.threads.config.ValidatorTestConfig;
import com.pluxurydolo.threads.config.WebTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    TokensTestConfig.class,
    ValidatorTestConfig.class,
    SchedulerTestConfig.class,
    WebTestConfig.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTests {
}
