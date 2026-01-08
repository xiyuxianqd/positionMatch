package com.xiyuxian.positionmatch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyuxian.positionmatch.model.dto.userapply.UserApplyAddRequest;
import com.xiyuxian.positionmatch.model.entity.UserApply;

public interface UserApplyService extends IService<UserApply> {

    Long addApply(UserApplyAddRequest applyAddRequest);

    Page<UserApply> listAppliesByUserId(Long userId, long current, long size);

    Boolean updateApplyStatus(Long id, String status);
}
