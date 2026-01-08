package com.xiyuxian.positionmatch.model.dto.userbehavior;

import com.xiyuxian.positionmatch.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBehaviorQueryRequest extends PageRequest implements Serializable {

    private Long userId;

    private Long positionId;

    private String behaviorType;
}
