package com.xiyuxian.positionmatch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyuxian.positionmatch.model.dto.userbehavior.UserBehaviorAddRequest;
import com.xiyuxian.positionmatch.model.dto.userbehavior.UserBehaviorQueryRequest;
import com.xiyuxian.positionmatch.model.entity.UserBehavior;

public interface UserBehaviorService extends IService<UserBehavior> {

    Long addBehavior(UserBehaviorAddRequest behaviorAddRequest);

    Boolean recordBehavior(Long userId, Long positionId, String behaviorType, Float rating);

    Page<UserBehavior> listBehaviorByPage(UserBehaviorQueryRequest behaviorQueryRequest);
}
