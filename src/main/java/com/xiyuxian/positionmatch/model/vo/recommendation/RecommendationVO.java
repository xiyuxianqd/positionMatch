package com.xiyuxian.positionmatch.model.vo.recommendation;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecommendationVO implements Serializable {

    private Long positionId;

    private String positionName;

    private String companyName;

    private String salaryRange;

    private String workLocation;

    private String education;

    private String major;

    private String skills;

    private Double score;

    private String reason;

    private String algorithmType;
}
