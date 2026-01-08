package com.xiyuxian.positionmatch.model.dto.userbehavior;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBehaviorAddRequest implements Serializable {

    private Long userId;

    private Long positionId;

    private String behaviorType;

    private Float rating;
}
