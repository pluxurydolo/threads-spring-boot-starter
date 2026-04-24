package com.pluxurydolo.threads.base;

import com.pluxurydolo.threads.TestApplication;
import com.pluxurydolo.threads.configuration.SchedulerTestConfiguration;
import com.pluxurydolo.threads.configuration.TokensTestConfiguration;
import com.pluxurydolo.threads.configuration.ValidatorTestConfiguration;
import com.pluxurydolo.threads.configuration.WebTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    TokensTestConfiguration.class,
    ValidatorTestConfiguration.class,
    SchedulerTestConfiguration.class,
    WebTestConfiguration.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTests {
}
