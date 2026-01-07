package com.xiyuxian.positionmatch.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuxian.positionmatch.annotation.AuthCheck;
import com.xiyuxian.positionmatch.common.BaseResponse;
import com.xiyuxian.positionmatch.common.DeleteRequest;
import com.xiyuxian.positionmatch.common.ResultUtils;
import com.xiyuxian.positionmatch.exception.BusinessException;
import com.xiyuxian.positionmatch.exception.ErrorCode;
import com.xiyuxian.positionmatch.exception.ThrowUtils;
import com.xiyuxian.positionmatch.model.dto.position.PositionAddRequest;
import com.xiyuxian.positionmatch.model.dto.position.PositionQueryRequest;
import com.xiyuxian.positionmatch.model.dto.position.PositionUpdateRequest;
import com.xiyuxian.positionmatch.model.entity.Position;
import com.xiyuxian.positionmatch.model.enums.UserRoleEnum;
import com.xiyuxian.positionmatch.model.vo.PositionVO;
import com.xiyuxian.positionmatch.service.PositionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Resource
    private PositionService positionService;

    @PostMapping("/add")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Long> addPosition(@RequestBody PositionAddRequest positionAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(positionAddRequest == null, ErrorCode.PARAMS_ERROR);
        Long result = positionService.addPosition(positionAddRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Boolean> updatePosition(@RequestBody PositionUpdateRequest positionUpdateRequest) {
        ThrowUtils.throwIf(positionUpdateRequest == null || positionUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.updatePosition(positionUpdateRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Boolean> deletePosition(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.deletePosition(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    @GetMapping("/get")
    public BaseResponse<PositionVO> getPositionById(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        PositionVO result = positionService.getPositionById(id);
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = "hr")
    public BaseResponse<Page<PositionVO>> listPositionVOByPage(@RequestBody PositionQueryRequest positionQueryRequest) {
        ThrowUtils.throwIf(positionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<PositionVO> result = positionService.listPositionVOByPage(positionQueryRequest);
        return ResultUtils.success(result);
    }

    @GetMapping("/list/vo")
    public BaseResponse<Page<PositionVO>> listPositionVOByPagePublic(PositionQueryRequest positionQueryRequest) {
        if (positionQueryRequest == null) {
            positionQueryRequest = new PositionQueryRequest();
        }
        Page<PositionVO> result = positionService.listPositionVOByPage(positionQueryRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/view")
    public BaseResponse<Boolean> incrementViewCount(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.incrementViewCount(id);
        return ResultUtils.success(result);
    }

    @PostMapping("/apply")
    public BaseResponse<Boolean> incrementApplyCount(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.incrementApplyCount(id);
        return ResultUtils.success(result);
    }
}
