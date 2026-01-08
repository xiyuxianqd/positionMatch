package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_collection")
public class UserCollection extends BaseEntity {

    private Long userId;

    private Long positionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
