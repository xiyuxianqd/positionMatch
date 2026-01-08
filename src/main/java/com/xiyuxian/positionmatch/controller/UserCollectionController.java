package com.xiyuxian.positionmatch.controller;

import com.xiyuxian.positionmatch.annotation.AuthCheck;
import com.xiyuxian.positionmatch.common.BaseResponse;
import com.xiyuxian.positionmatch.common.ResultUtils;
import com.xiyuxian.positionmatch.constant.UserConstant;
import com.xiyuxian.positionmatch.manager.auth.StpKit;
import com.xiyuxian.positionmatch.model.dto.usercollection.UserCollectionAddRequest;
import com.xiyuxian.positionmatch.model.entity.UserCollection;
import com.xiyuxian.positionmatch.service.UserCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户收藏管理")
@RestController
@RequestMapping("/userCollection")
@Slf4j
public class UserCollectionController {

    @Resource
    private UserCollectionService userCollectionService;

    @ApiOperation("添加收藏")
    @PostMapping("/add")
    public BaseResponse<Long> addCollection(@RequestBody UserCollectionAddRequest collectionAddRequest) {
        Long userId = StpKit.getLoginId();
        collectionAddRequest.setUserId(userId);
        Long collectionId = userCollectionService.addCollection(collectionAddRequest);
        return ResultUtils.success(collectionId);
    }

    @ApiOperation("取消收藏")
    @DeleteMapping("/remove")
    public BaseResponse<Boolean> removeCollection(
            @RequestParam Long positionId) {
        Long userId = StpKit.getLoginId();
        Boolean result = userCollectionService.removeCollection(userId, positionId);
        return ResultUtils.success(result);
    }

    @ApiOperation("获取用户收藏列表")
    @GetMapping("/list")
    public BaseResponse<List<UserCollection>> listCollections() {
        Long userId = StpKit.getLoginId();
        List<UserCollection> collections = userCollectionService.listCollectionsByUserId(userId);
        return ResultUtils.success(collections);
    }

    @ApiOperation("检查是否已收藏")
    @GetMapping("/check")
    public BaseResponse<Boolean> isCollected(@RequestParam Long positionId) {
        Long userId = StpKit.getLoginId();
        Boolean result = userCollectionService.isCollected(userId, positionId);
        return ResultUtils.success(result);
    }
}
