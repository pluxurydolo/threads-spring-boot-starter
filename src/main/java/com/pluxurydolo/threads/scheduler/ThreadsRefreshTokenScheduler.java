package com.pluxurydolo.threads.scheduler;

import com.pluxurydolo.threads.scheduler.handler.ThreadsRefreshTokenSchedulerHandler;
import org.springframework.scheduling.annotation.Scheduled;

public class ThreadsRefreshTokenScheduler {
    private final ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler;

    public ThreadsRefreshTokenScheduler(ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler) {
        this.threadsRefreshTokenSchedulerHandler = threadsRefreshTokenSchedulerHandler;
    }

    @Scheduled(
        cron = "${threads.scheduler.refresh-token.cron}",
        zone = "${threads.scheduler.refresh-token.zone}"
    )
    public void schedule() {
        String jobName = getClass().getName();

        threadsRefreshTokenSchedulerHandler.handle(jobName)
            .subscribe();
    }
}
