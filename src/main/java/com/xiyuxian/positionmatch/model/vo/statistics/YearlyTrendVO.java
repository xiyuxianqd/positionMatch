package com.xiyuxian.positionmatch.model.vo.statistics;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearlyTrendVO implements Serializable {

    private Integer year;

    private Integer totalGraduates;

    private Integer employedGraduates;

    private Double employmentRate;

    private Integer averageSalary;
}
