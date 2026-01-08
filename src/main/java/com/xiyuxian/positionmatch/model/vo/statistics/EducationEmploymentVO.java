package com.xiyuxian.positionmatch.model.vo.statistics;

import lombok.Data;

import java.io.Serializable;

@Data
public class EducationEmploymentVO implements Serializable {

    private String education;

    private Integer totalStudents;

    private Integer employedStudents;

    private Double employmentRate;

    private Integer averageSalary;
}
