package com.xiyuxian.positionmatch.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 8735650154179439661L;

    private String userAccount;

    private String userPassword;
}
