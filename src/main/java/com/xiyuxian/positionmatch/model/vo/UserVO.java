package com.xiyuxian.positionmatch.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {

    private Long id;

    private String userAccount;

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

    private Date createTime;

    private static final long serialVersionUID = 1L;
}
