package com.xiyuxian.positionmatch.model.vo.statistics;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmploymentStatisticsVO implements Serializable {

    private Integer totalGraduates;

    private Integer employedGraduates;

    private Integer employmentRate;

    private Integer averageSalary;

    private String topIndustry;

    private Integer topIndustryCount;
}
