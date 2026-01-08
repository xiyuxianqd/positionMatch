package com.xiyuxian.positionmatch.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuxian.positionmatch.annotation.AuthCheck;
import com.xiyuxian.positionmatch.common.BaseResponse;
import com.xiyuxian.positionmatch.common.ResultUtils;
import com.xiyuxian.positionmatch.constant.UserConstant;
import com.xiyuxian.positionmatch.manager.auth.StpKit;
import com.xiyuxian.positionmatch.model.dto.userapply.UserApplyAddRequest;
import com.xiyuxian.positionmatch.model.entity.UserApply;
import com.xiyuxian.positionmatch.service.UserApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户申请管理")
@RestController
@RequestMapping("/userApply")
@Slf4j
public class UserApplyController {

    @Resource
    private UserApplyService userApplyService;

    @ApiOperation("添加申请")
    @PostMapping("/add")
    public BaseResponse<Long> addApply(@RequestBody UserApplyAddRequest applyAddRequest) {
        Long userId = StpKit.getLoginId();
        applyAddRequest.setUserId(userId);
        Long applyId = userApplyService.addApply(applyAddRequest);
        return ResultUtils.success(applyId);
    }

    @ApiOperation("获取用户申请列表")
    @GetMapping("/list")
    public BaseResponse<Page<UserApply>> listApplies(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        Long userId = StpKit.getLoginId();
        Page<UserApply> applyPage = userApplyService.listAppliesByUserId(userId, current, size);
        return ResultUtils.success(applyPage);
    }

    @ApiOperation("更新申请状态")
    @PostMapping("/updateStatus")
    @AuthCheck(mustRole = UserConstant.HR_ROLE)
    public BaseResponse<Boolean> updateApplyStatus(
            @RequestParam Long id,
            @RequestParam String status) {
        Boolean result = userApplyService.updateApplyStatus(id, status);
        return ResultUtils.success(result);
    }
}
