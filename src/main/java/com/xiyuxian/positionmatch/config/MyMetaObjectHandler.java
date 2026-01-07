package com.xiyuxian.positionmatch.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger log = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Long userId = getCurrentUserId();
            String userName = getCurrentUserName();

            this.setFieldValByName("addUserId", userId, metaObject);
            this.setFieldValByName("addUserName", userName, metaObject);
            this.setFieldValByName("createTime", new Date(), metaObject);
        } catch (Exception e) {
            log.error("自动填充插入字段失败", e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            Long userId = getCurrentUserId();
            String userName = getCurrentUserName();

            this.setFieldValByName("editUserId", userId, metaObject);
            this.setFieldValByName("editUserName", userName, metaObject);
            this.setFieldValByName("updateTime", new Date(), metaObject);
        } catch (Exception e) {
            log.error("自动填充更新字段失败", e);
        }
    }

    private Long getCurrentUserId() {
        try {
            return StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            log.warn("获取当前用户ID失败", e);
            return null;
        }
    }

    private String getCurrentUserName() {
        try {
            Object user = StpUtil.getSession().get("user");
            if (user != null) {
                return user.toString();
            }
            return null;
        } catch (Exception e) {
            log.warn("获取当前用户名失败", e);
            return null;
        }
    }
}
