package com.xiyuxian.positionmatch.scheduler.manager;

import com.xiyuxian.positionmatch.scheduler.job.CleanExpiredBehaviorJob;
import com.xiyuxian.positionmatch.scheduler.job.GenerateEmploymentReportJob;
import com.xiyuxian.positionmatch.scheduler.job.TrainRecommendationModelJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class SchedulerManager {

    @Resource
    private Scheduler scheduler;

    public void initRecommendationTasks() {
        try {
            initTrainModelTask();
            initGenerateReportTask();
            initCleanDataTask();
            log.info("推荐系统定时任务初始化完成");
        } catch (Exception e) {
            log.error("初始化推荐系统定时任务失败", e);
        }
    }

    private void initTrainModelTask() {
        String jobName = "TrainRecommendationModelJob";
        String groupName = "RECOMMENDATION_GROUP";

        try {
            if (scheduler.checkExists(JobKey.jobKey(jobName, groupName))) {
                log.info("推荐模型训练任务已存在，跳过创建");
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(TrainRecommendationModelJob.class)
                    .withIdentity(jobName, groupName)
                    .storeDurably()
                    .build();

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + "Trigger", groupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ?"))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            log.info("推荐模型训练任务创建成功，每天凌晨2点执行");
        } catch (Exception e) {
            log.error("创建推荐模型训练任务失败", e);
        }
    }

    private void initGenerateReportTask() {
        String jobName = "GenerateEmploymentReportJob";
        String groupName = "RECOMMENDATION_GROUP";

        try {
            if (scheduler.checkExists(JobKey.jobKey(jobName, groupName))) {
                log.info("就业报告生成任务已存在，跳过创建");
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(GenerateEmploymentReportJob.class)
                    .withIdentity(jobName, groupName)
                    .storeDurably()
                    .build();

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + "Trigger", groupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 3 * * ?"))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            log.info("就业报告生成任务创建成功，每天凌晨3点执行");
        } catch (Exception e) {
            log.error("创建就业报告生成任务失败", e);
        }
    }

    private void initCleanDataTask() {
        String jobName = "CleanExpiredBehaviorJob";
        String groupName = "RECOMMENDATION_GROUP";

        try {
            if (scheduler.checkExists(JobKey.jobKey(jobName, groupName))) {
                log.info("清理过期数据任务已存在，跳过创建");
                return;
            }

            JobDetail jobDetail = JobBuilder.newJob(CleanExpiredBehaviorJob.class)
                    .withIdentity(jobName, groupName)
                    .storeDurably()
                    .build();

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + "Trigger", groupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 * * ?"))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            log.info("清理过期数据任务创建成功，每天凌晨4点执行");
        } catch (Exception e) {
            log.error("创建清理过期数据任务失败", e);
        }
    }

    public void runTaskNow(String jobName, String groupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, groupName);
            if (scheduler.checkExists(jobKey)) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("taskName", jobName);
                jobDataMap.put("beanName", jobName);
                jobDataMap.put("group", groupName);

                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName + "ImmediateTrigger", groupName)
                        .forJob(jobDetail)
                        .usingJobData(jobDataMap)
                        .startNow()
                        .build();

                scheduler.scheduleJob(trigger);
                log.info("立即执行任务: {}", jobName);
            } else {
                log.warn("任务不存在: {}", jobName);
            }
        } catch (Exception e) {
            log.error("立即执行任务失败: {}", jobName, e);
        }
    }
}
