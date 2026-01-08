package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_behavior")
public class UserBehavior extends BaseEntity {

    private Long userId;

    private Long positionId;

    private String behaviorType;

    private Float rating;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
