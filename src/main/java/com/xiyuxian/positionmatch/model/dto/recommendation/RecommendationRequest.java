package com.xiyuxian.positionmatch.model.dto.recommendation;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecommendationRequest implements Serializable {

    private Long userId;

    private String algorithmType;

    private Integer n;
}
