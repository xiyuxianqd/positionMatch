package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "company")
public class Company extends BaseEntity {

    @Version
    private Integer version;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
