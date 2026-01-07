package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "position")
public class Position implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
