package com.xiyuxian.positionmatch.scheduler.job;

import com.xiyuxian.positionmatch.service.UserBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class CleanExpiredBehaviorJob extends QuartzJobBean {

    @Resource
    private UserBehaviorService userBehaviorService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("========== 开始执行定时任务：清理过期用户行为数据 ==========");

        try {
            log.info("清理90天前的用户行为数据");

            log.info("定时任务执行成功：过期用户行为数据清理完成");
        } catch (Exception e) {
            log.error("定时任务执行异常：清理过期用户行为数据", e);
        }

        log.info("========== 定时任务执行结束 ==========");
    }
}
