package com.xiyuxian.positionmatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig {

    public ScheduleConfig() {
        log.info("定时任务配置加载完成");
    }
}
