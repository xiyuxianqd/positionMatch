package com.xiyuxian.positionmatch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuxian.positionmatch.common.RestClientUtil;
import com.xiyuxian.positionmatch.manager.auth.StpKit;
import com.xiyuxian.positionmatch.mapper.UserBehaviorMapper;
import com.xiyuxian.positionmatch.model.dto.userbehavior.UserBehaviorAddRequest;
import com.xiyuxian.positionmatch.model.dto.userbehavior.UserBehaviorQueryRequest;
import com.xiyuxian.positionmatch.model.entity.Position;
import com.xiyuxian.positionmatch.model.entity.UserBehavior;
import com.xiyuxian.positionmatch.service.PositionService;
import com.xiyuxian.positionmatch.service.RecommendationService;
import com.xiyuxian.positionmatch.service.UserBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserBehaviorServiceImpl extends ServiceImpl<UserBehaviorMapper, UserBehavior> implements UserBehaviorService {

    @Resource
    private PositionService positionService;

    @Resource
    private RecommendationService recommendationService;

    @Resource
    private RestClientUtil restClientUtil;

    @Value("${recommendation.python.api.url:http://localhost:8000}")
    private String pythonApiUrl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addBehavior(UserBehaviorAddRequest behaviorAddRequest) {
        UserBehavior userBehavior = new UserBehavior();
        BeanUtils.copyProperties(behaviorAddRequest, userBehavior);
        boolean result = this.save(userBehavior);

        if (!result) {
            throw new RuntimeException("添加用户行为失败");
        }

        return userBehavior.getId();
    }

    @Override
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Boolean recordBehavior(Long userId, Long positionId, String behaviorType, Float rating) {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setUserId(userId);
        userBehavior.setPositionId(positionId);
        userBehavior.setBehaviorType(behaviorType);
        userBehavior.setRating(rating);

        boolean result = this.save(userBehavior);

        if (!result) {
            log.error("记录用户行为失败: userId={}, positionId={}, behaviorType={}", userId, positionId, behaviorType);
            return false;
        }

        if ("view".equals(behaviorType)) {
            positionService.incrementViewCount(positionId);
        } else if ("apply".equals(behaviorType)) {
            positionService.incrementApplyCount(positionId);
        }

        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", userId);
            requestBody.put("position_id", positionId);
            requestBody.put("behavior_type", behaviorType);
            requestBody.put("rating", rating);

            String url = pythonApiUrl + "/api/recommendation/behavior";
            String response = restClientUtil.post(url, requestBody);
            log.info("异步调用Python记录行为API: url={}, response={}", url, response);
        } catch (Exception e) {
            log.error("异步调用Python记录行为API失败", e);
        }

        return true;
    }

    @Override
    public Page<UserBehavior> listBehaviorByPage(UserBehaviorQueryRequest behaviorQueryRequest) {
        long current = behaviorQueryRequest.getCurrent();
        long size = behaviorQueryRequest.getPageSize();
        QueryWrapper<UserBehavior> queryWrapper = this.getQueryWrapper(behaviorQueryRequest);
        return this.page(new Page<>(current, size), queryWrapper);
    }

    private QueryWrapper<UserBehavior> getQueryWrapper(UserBehaviorQueryRequest behaviorQueryRequest) {
        QueryWrapper<UserBehavior> queryWrapper = new QueryWrapper<>();

        Long userId = behaviorQueryRequest.getUserId();
        Long positionId = behaviorQueryRequest.getPositionId();
        String behaviorType = behaviorQueryRequest.getBehaviorType();
        String sortField = behaviorQueryRequest.getSortField();
        String sortOrder = behaviorQueryRequest.getSortOrder();

        queryWrapper.eq(userId != null, "user_id", userId);
        queryWrapper.eq(positionId != null, "position_id", positionId);
        queryWrapper.eq(StrUtil.isNotBlank(behaviorType), "behavior_type", behaviorType);

        queryWrapper.orderBy(StrUtil.isNotBlank(sortField), "asc".equals(sortOrder), sortField);
        queryWrapper.orderByDesc("create_time");

        return queryWrapper;
    }
}
