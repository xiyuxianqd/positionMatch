package com.xiyuxian.positionmatch.model.vo.statistics;

import lombok.Data;

import java.io.Serializable;

@Data
public class MajorEmploymentVO implements Serializable {

    private String major;

    private Integer totalStudents;

    private Integer employedStudents;

    private Double employmentRate;

    private Integer averageSalary;

    private String topCompany;
}
