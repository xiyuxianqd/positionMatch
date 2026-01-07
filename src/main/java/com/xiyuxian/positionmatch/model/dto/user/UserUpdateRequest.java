package com.xiyuxian.positionmatch.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户更新请求")
public class UserUpdateRequest implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "用户简介")
    private String userProfile;

    @ApiModelProperty(value = "用户角色")
    private String userRole;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "技能")
    private String skills;

    @ApiModelProperty(value = "毕业年份")
    private Integer graduationYear;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "简历链接")
    private String resumeUrl;

    @ApiModelProperty(value = "用户状态")
    private Integer userStatus;

    private static final long serialVersionUID = 1L;
}
