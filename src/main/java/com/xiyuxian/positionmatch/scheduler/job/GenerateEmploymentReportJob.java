package com.xiyuxian.positionmatch.scheduler.job;

import com.xiyuxian.positionmatch.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class GenerateEmploymentReportJob extends QuartzJobBean {

    @Resource
    private StatisticsService statisticsService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("========== 开始执行定时任务：生成就业数据报告 ==========");

        try {
            log.info("生成整体就业统计");
            statisticsService.getEmploymentStatistics();

            log.info("生成专业就业统计");
            statisticsService.getMajorEmploymentStatistics();

            log.info("生成学历就业统计");
            statisticsService.getEducationEmploymentStatistics();

            log.info("生成行业分布统计");
            statisticsService.getIndustryDistribution();

            log.info("生成年度趋势统计");
            statisticsService.getYearlyTrend();

            log.info("定时任务执行成功：就业数据报告生成完成");
        } catch (Exception e) {
            log.error("定时任务执行异常：生成就业数据报告", e);
        }

        log.info("========== 定时任务执行结束 ==========");
    }
}
