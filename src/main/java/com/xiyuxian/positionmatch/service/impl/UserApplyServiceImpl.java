package com.xiyuxian.positionmatch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuxian.positionmatch.mapper.UserApplyMapper;
import com.xiyuxian.positionmatch.model.dto.userapply.UserApplyAddRequest;
import com.xiyuxian.positionmatch.model.entity.UserApply;
import com.xiyuxian.positionmatch.service.UserApplyService;
import com.xiyuxian.positionmatch.service.UserBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserApplyServiceImpl extends ServiceImpl<UserApplyMapper, UserApply> implements UserApplyService {

    @Resource
    private UserBehaviorService userBehaviorService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addApply(UserApplyAddRequest applyAddRequest) {
        UserApply userApply = new UserApply();
        BeanUtils.copyProperties(applyAddRequest, userApply);
        userApply.setApplyStatus("pending");

        boolean result = this.save(userApply);

        if (!result) {
            throw new RuntimeException("申请失败");
        }

        userBehaviorService.recordBehavior(applyAddRequest.getUserId(), applyAddRequest.getPositionId(), "apply", 0.9f);

        return userApply.getId();
    }

    @Override
    public Page<UserApply> listAppliesByUserId(Long userId, long current, long size) {
        QueryWrapper<UserApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        return this.page(new Page<>(current, size), queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateApplyStatus(Long id, String status) {
        UserApply userApply = new UserApply();
        userApply.setId(id);
        userApply.setApplyStatus(status);

        return this.updateById(userApply);
    }
}
