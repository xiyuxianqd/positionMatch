package com.xiyuxian.positionmatch.model.dto.user;

import com.xiyuxian.positionmatch.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private String userName;

    private String userAccount;

    private String userProfile;

    private String userRole;

    private String education;

    private String major;

    private Integer graduationYear;

    private String school;

    private String companyName;

    private Integer userStatus;

    private static final long serialVersionUID = 1L;
}
