package com.xiyuxian.positionmatch.controller;

import com.xiyuxian.positionmatch.annotation.AuthCheck;
import com.xiyuxian.positionmatch.common.BaseResponse;
import com.xiyuxian.positionmatch.common.ResultUtils;
import com.xiyuxian.positionmatch.constant.UserConstant;
import com.xiyuxian.positionmatch.model.vo.statistics.*;
import com.xiyuxian.positionmatch.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "就业数据分析")
@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @ApiOperation("获取就业统计数据")
    @GetMapping("/employment")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<EmploymentStatisticsVO> getEmploymentStatistics() {
        EmploymentStatisticsVO statistics = statisticsService.getEmploymentStatistics();
        return ResultUtils.success(statistics);
    }

    @ApiOperation("按专业统计就业情况")
    @GetMapping("/major")
    public BaseResponse<List<MajorEmploymentVO>> getMajorEmploymentStatistics() {
        List<MajorEmploymentVO> statistics = statisticsService.getMajorEmploymentStatistics();
        return ResultUtils.success(statistics);
    }

    @ApiOperation("按学历统计就业情况")
    @GetMapping("/education")
    public BaseResponse<List<EducationEmploymentVO>> getEducationEmploymentStatistics() {
        List<EducationEmploymentVO> statistics = statisticsService.getEducationEmploymentStatistics();
        return ResultUtils.success(statistics);
    }

    @ApiOperation("获取行业分布")
    @GetMapping("/industry")
    public BaseResponse<List<IndustryDistributionVO>> getIndustryDistribution() {
        List<IndustryDistributionVO> distribution = statisticsService.getIndustryDistribution();
        return ResultUtils.success(distribution);
    }

    @ApiOperation("获取年度就业趋势")
    @GetMapping("/yearly")
    public BaseResponse<List<YearlyTrendVO>> getYearlyTrend() {
        List<YearlyTrendVO> trend = statisticsService.getYearlyTrend();
        return ResultUtils.success(trend);
    }

    @ApiOperation("按专业获取年度就业趋势")
    @GetMapping("/yearly/major")
    public BaseResponse<List<YearlyTrendVO>> getYearlyTrendByMajor(@RequestParam String major) {
        List<YearlyTrendVO> trend = statisticsService.getYearlyTrendByMajor(major);
        return ResultUtils.success(trend);
    }
}
