package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "company")
public class Company implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String companyName;

    private String companyDesc;

    private String industry;

    private String companySize;

    private String companyAddress;

    private String companyLogo;

    private String website;

    private String contactPerson;

    private String contactPhone;

    private String contactEmail;

    private Integer companyStatus;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
