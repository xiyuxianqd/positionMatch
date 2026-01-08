package com.xiyuxian.positionmatch.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuxian.positionmatch.annotation.AuthCheck;
import com.xiyuxian.positionmatch.common.BaseResponse;
import com.xiyuxian.positionmatch.common.DeleteRequest;
import com.xiyuxian.positionmatch.common.ResultUtils;
import com.xiyuxian.positionmatch.constant.UserConstant;
import com.xiyuxian.positionmatch.manager.auth.StpKit;
import com.xiyuxian.positionmatch.model.dto.userbehavior.UserBehaviorAddRequest;
import com.xiyuxian.positionmatch.model.dto.userbehavior.UserBehaviorQueryRequest;
import com.xiyuxian.positionmatch.model.entity.UserBehavior;
import com.xiyuxian.positionmatch.service.UserBehaviorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户行为管理")
@RestController
@RequestMapping("/userBehavior")
@Slf4j
public class UserBehaviorController {

    @Resource
    private UserBehaviorService userBehaviorService;

    @ApiOperation("添加用户行为")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addBehavior(@RequestBody UserBehaviorAddRequest behaviorAddRequest) {
        Long behaviorId = userBehaviorService.addBehavior(behaviorAddRequest);
        return ResultUtils.success(behaviorId);
    }

    @ApiOperation("记录用户行为")
    @PostMapping("/record")
    public BaseResponse<Boolean> recordBehavior(
            @RequestParam Long userId,
            @RequestParam Long positionId,
            @RequestParam String behaviorType,
            @RequestParam(required = false, defaultValue = "0.5") Float rating) {
        Boolean result = userBehaviorService.recordBehavior(userId, positionId, behaviorType, rating);
        return ResultUtils.success(result);
    }

    @ApiOperation("分页查询用户行为")
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserBehavior>> listBehaviorByPage(@RequestBody UserBehaviorQueryRequest behaviorQueryRequest) {
        Page<UserBehavior> behaviorPage = userBehaviorService.listBehaviorByPage(behaviorQueryRequest);
        return ResultUtils.success(behaviorPage);
    }

    @ApiOperation("删除用户行为")
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteBehavior(@RequestBody DeleteRequest deleteRequest) {
        boolean result = userBehaviorService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }
}
