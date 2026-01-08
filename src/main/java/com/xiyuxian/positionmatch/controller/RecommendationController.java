package com.xiyuxian.positionmatch.controller;

import com.xiyuxian.positionmatch.annotation.AuthCheck;
import com.xiyuxian.positionmatch.common.BaseResponse;
import com.xiyuxian.positionmatch.common.ResultUtils;
import com.xiyuxian.positionmatch.constant.UserConstant;
import com.xiyuxian.positionmatch.manager.auth.StpKit;
import com.xiyuxian.positionmatch.model.dto.recommendation.RecommendationRequest;
import com.xiyuxian.positionmatch.model.dto.recommendation.TrainModelRequest;
import com.xiyuxian.positionmatch.model.vo.recommendation.RecommendationVO;
import com.xiyuxian.positionmatch.model.vo.recommendation.SimilarPositionVO;
import com.xiyuxian.positionmatch.model.vo.recommendation.SimilarUserVO;
import com.xiyuxian.positionmatch.service.RecommendationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "岗位推荐管理")
@RestController
@RequestMapping("/recommendation")
@Slf4j
public class RecommendationController {

    @Resource
    private RecommendationService recommendationService;

    @ApiOperation("获取职位推荐")
    @PostMapping("/list")
    public BaseResponse<List<RecommendationVO>> getRecommendations(@RequestBody RecommendationRequest request) {
        Long userId = StpKit.getLoginId();
        request.setUserId(userId);
        List<RecommendationVO> recommendations = recommendationService.getRecommendations(request);
        return ResultUtils.success(recommendations);
    }

    @ApiOperation("获取相似用户")
    @GetMapping("/similar-users")
    public BaseResponse<List<SimilarUserVO>> getSimilarUsers(
            @RequestParam(required = false, defaultValue = "5") Integer n) {
        Long userId = StpKit.getLoginId();
        List<SimilarUserVO> similarUsers = recommendationService.getSimilarUsers(userId, n);
        return ResultUtils.success(similarUsers);
    }

    @ApiOperation("获取相似职位")
    @GetMapping("/similar-positions")
    public BaseResponse<List<SimilarPositionVO>> getSimilarPositions(
            @RequestParam Long positionId,
            @RequestParam(required = false, defaultValue = "10") Integer n) {
        List<SimilarPositionVO> similarPositions = recommendationService.getSimilarPositions(positionId, n);
        return ResultUtils.success(similarPositions);
    }

    @ApiOperation("训练推荐模型")
    @PostMapping("/train")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> trainModel(@RequestBody TrainModelRequest request) {
        Boolean result = recommendationService.trainModel(request);
        return ResultUtils.success(result);
    }

    @ApiOperation("手动触发模型训练")
    @PostMapping("/train/force")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> forceTrainModel() {
        TrainModelRequest request = new TrainModelRequest();
        request.setForceRetrain(true);
        Boolean result = recommendationService.trainModel(request);
        return ResultUtils.success(result);
    }
}
