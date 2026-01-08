package com.xiyuxian.positionmatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.xiyuxian.positionmatch.scheduler.manager.SchedulerManager;

@Slf4j
@Component
public class ApplicationStartupRunner implements ApplicationRunner {

    @Autowired
    private SchedulerManager schedulerManager;

    @Override
    public void run(ApplicationArguments args) {
        log.info("========== 应用启动，开始初始化推荐系统定时任务 ==========");
        try {
            schedulerManager.initRecommendationTasks();
            log.info("========== 推荐系统定时任务初始化完成 ==========");
        } catch (Exception e) {
            log.error("推荐系统定时任务初始化失败", e);
        }
    }
}
