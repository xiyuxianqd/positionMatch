package com.xiyuxian.positionmatch.model.dto.user;

import com.xiyuxian.positionmatch.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "用户查询请求")
public class UserQueryRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "用户简介")
    private String userProfile;

    @ApiModelProperty(value = "用户角色")
    private String userRole;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "毕业年份")
    private Integer graduationYear;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "用户状态")
    private Integer userStatus;

    private static final long serialVersionUID = 1L;
}
