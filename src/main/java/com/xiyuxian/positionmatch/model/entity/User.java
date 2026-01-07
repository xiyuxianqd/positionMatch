package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
public class User extends BaseEntity {

    @Version
    private Integer version;

    private String userAccount;

    private String userPassword;

    private String userName;

    private String userAvatar;

    private String userProfile;

    private String userRole;

    private String education;

    private String major;

    private String skills;

    private Integer graduationYear;

    private String school;

    private String companyName;

    private String position;

    private String phone;

    private String email;

    private String resumeUrl;

    private Integer userStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
