package com.xiyuxian.positionmatch.manager.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.xiyuxian.positionmatch.model.entity.User;
import com.xiyuxian.positionmatch.model.enums.UserRoleEnum;
import com.xiyuxian.positionmatch.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserService userService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        User user = userService.getById((Long) loginId);
        if (user == null) {
            return new ArrayList<>();
        }
        List<String> roleList = new ArrayList<>();
        roleList.add(user.getUserRole());
        return roleList;
    }
}
