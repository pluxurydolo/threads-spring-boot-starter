package com.pluxurydolo.threads.scheduler;

import com.pluxurydolo.threads.scheduler.handler.ThreadsRefreshTokenSchedulerHandler;
import org.springframework.scheduling.annotation.Scheduled;

public class ThreadsRefreshTokenScheduler {
    private final ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler;

    public ThreadsRefreshTokenScheduler(ThreadsRefreshTokenSchedulerHandler threadsRefreshTokenSchedulerHandler) {
        this.threadsRefreshTokenSchedulerHandler = threadsRefreshTokenSchedulerHandler;
    }

    @Scheduled(
        cron = "${threads.refresh.token.scheduler.cron}",
        zone = "${threads.refresh.token.scheduler.zone}"
    )
    public void refreshToken() {
        String jobName = getClass().getName();

        threadsRefreshTokenSchedulerHandler.handle(jobName)
            .subscribe();
    }
}
