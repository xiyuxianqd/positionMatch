package com.xiyuxian.positionmatch.model.vo.statistics;

import lombok.Data;

import java.io.Serializable;

@Data
public class IndustryDistributionVO implements Serializable {

    private String industry;

    private Integer count;

    private Double percentage;
}
