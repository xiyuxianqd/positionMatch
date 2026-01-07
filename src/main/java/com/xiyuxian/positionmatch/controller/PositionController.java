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
import com.xiyuxian.positionmatch.model.vo.PositionVO;
import com.xiyuxian.positionmatch.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "职位管理")
@RestController
@RequestMapping("/position")
public class PositionController {

    @Resource
    private PositionService positionService;

    @PostMapping("/add")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "添加职位")
    public BaseResponse<Long> addPosition(@Valid @RequestBody PositionAddRequest positionAddRequest) {
        Long result = positionService.addPosition(positionAddRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "更新职位")
    public BaseResponse<Boolean> updatePosition(@Valid @RequestBody PositionUpdateRequest positionUpdateRequest) {
        ThrowUtils.throwIf(positionUpdateRequest == null || positionUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.updatePosition(positionUpdateRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "删除职位")
    public BaseResponse<Boolean> deletePosition(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.deletePosition(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取职位详情")
    public BaseResponse<PositionVO> getPositionById(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        PositionVO result = positionService.getPositionById(id);
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = "hr")
    @ApiOperation(value = "分页查询职位（HR）")
    public BaseResponse<Page<PositionVO>> listPositionVOByPage(@Valid @RequestBody PositionQueryRequest positionQueryRequest) {
        ThrowUtils.throwIf(positionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<PositionVO> result = positionService.listPositionVOByPage(positionQueryRequest);
        return ResultUtils.success(result);
    }

    @GetMapping("/list/vo")
    @ApiOperation(value = "分页查询职位（公开）")
    public BaseResponse<Page<PositionVO>> listPositionVOByPagePublic(PositionQueryRequest positionQueryRequest) {
        if (positionQueryRequest == null) {
            positionQueryRequest = new PositionQueryRequest();
        }
        Page<PositionVO> result = positionService.listPositionVOByPage(positionQueryRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/view")
    @ApiOperation(value = "增加浏览次数")
    public BaseResponse<Boolean> incrementViewCount(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.incrementViewCount(id);
        return ResultUtils.success(result);
    }

    @PostMapping("/apply")
    @ApiOperation(value = "增加申请次数")
    public BaseResponse<Boolean> incrementApplyCount(@RequestParam Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        Boolean result = positionService.incrementApplyCount(id);
        return ResultUtils.success(result);
    }
}
