package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_apply")
public class UserApply extends BaseEntity {

    private Long userId;

    private Long positionId;

    private String applyStatus;

    private String resumeUrl;

    private String coverLetter;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
