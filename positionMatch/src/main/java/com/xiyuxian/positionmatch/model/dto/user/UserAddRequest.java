package com.xiyuxian.positionmatch.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}
