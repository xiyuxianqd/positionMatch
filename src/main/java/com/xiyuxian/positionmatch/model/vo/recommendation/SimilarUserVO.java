package com.xiyuxian.positionmatch.model.vo.recommendation;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimilarUserVO implements Serializable {

    private Long userId;

    private String userName;

    private String education;

    private String major;

    private String school;

    private String companyName;

    private String position;

    private Double similarity;

    private Integer commonItems;
}
