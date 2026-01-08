package com.xiyuxian.positionmatch.scheduler.job;

import com.xiyuxian.positionmatch.model.dto.recommendation.TrainModelRequest;
import com.xiyuxian.positionmatch.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class TrainRecommendationModelJob extends QuartzJobBean {

    @Resource
    private RecommendationService recommendationService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("========== 开始执行定时任务：训练推荐模型 ==========");

        try {
            TrainModelRequest request = new TrainModelRequest();
            request.setForceRetrain(false);

            Boolean result = recommendationService.trainModel(request);
            if (result) {
                log.info("定时任务执行成功：推荐模型训练完成");
            } else {
                log.warn("定时任务执行失败：推荐模型训练失败或正在训练中");
            }
        } catch (Exception e) {
            log.error("定时任务执行异常：训练推荐模型", e);
        }

        log.info("========== 定时任务执行结束 ==========");
    }
}
