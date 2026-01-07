package com.xiyuxian.positionmatch.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户登录请求")
public class UserLoginRequest implements Serializable {

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "用户密码")
    private String userPassword;

    private static final long serialVersionUID = 8735650154179439661L;
}
