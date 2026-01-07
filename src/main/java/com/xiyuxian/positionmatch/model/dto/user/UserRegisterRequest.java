package com.xiyuxian.positionmatch.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户注册请求")
public class UserRegisterRequest implements Serializable {

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "用户密码")
    private String userPassword;

    @ApiModelProperty(value = "确认密码")
    private String checkPassword;

    @ApiModelProperty(value = "用户角色")
    private String userRole;

    private static final long serialVersionUID = 8735650154179439661L;
}
