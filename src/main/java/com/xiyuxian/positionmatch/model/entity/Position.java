package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "position")
public class Position extends BaseEntity {

    @Version
    private Integer version;

    private String positionName;

    private String companyName;

    private Long companyId;

    private String positionDesc;

    private String requirements;

    private String salaryRange;

    private String workLocation;

    private String education;

    private String major;

    private String skills;

    private Integer workYears;

    private String positionType;

    private String tags;

    private Integer positionStatus;

    private Long viewCount;

    private Long applyCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
