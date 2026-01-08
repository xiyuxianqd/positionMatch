package com.xiyuxian.positionmatch.model.vo.recommendation;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimilarPositionVO implements Serializable {

    private Long positionId;

    private String positionName;

    private String companyName;

    private String salaryRange;

    private String workLocation;

    private Double similarity;

    private Integer commonUsers;
}
