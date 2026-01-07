package com.xiyuxian.positionmatch.manager.auth;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

/**
 * StpLogic 门面类，管理项目中所有的 StpLogic 账号体系
 * 添加 @Component 注解的目的是确保静态属性 DEFAULT 和 SPACE 被初始化
 */
@Component
public class StpKit {

    public static final String LOGIN_TYPE = "login";

    public static void login(Long userId) {
        StpUtil.login(userId, LOGIN_TYPE);
    }

    public static void logout() {
        StpUtil.logout();
    }

    public static Long getLoginId() {
        return StpUtil.getLoginIdAsLong();
    }

    public static boolean isLogin() {
        return StpUtil.isLogin();
    }
}
