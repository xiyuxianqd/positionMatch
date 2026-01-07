package com.xiyuxian.positionmatch.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "user")
public class User implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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

    private Date editTime;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
