package com.xiyuxian.positionmatch.service;

import com.xiyuxian.positionmatch.model.vo.statistics.*;

import java.util.List;

public interface StatisticsService {

    EmploymentStatisticsVO getEmploymentStatistics();

    List<MajorEmploymentVO> getMajorEmploymentStatistics();

    List<EducationEmploymentVO> getEducationEmploymentStatistics();

    List<IndustryDistributionVO> getIndustryDistribution();

    List<YearlyTrendVO> getYearlyTrend();

    List<YearlyTrendVO> getYearlyTrendByMajor(String major);
}
