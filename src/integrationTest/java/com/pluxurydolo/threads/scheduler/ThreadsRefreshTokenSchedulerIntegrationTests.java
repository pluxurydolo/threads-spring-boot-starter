package com.pluxurydolo.threads.scheduler;

import com.pluxurydolo.threads.base.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ThreadsRefreshTokenSchedulerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ThreadsRefreshTokenScheduler scheduler;

    @Test
    void testSchedule() {
        assertDoesNotThrow(scheduler::schedule);
    }
}
