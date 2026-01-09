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
        User user = userService.getById((Long) loginId);
        if (user == null) {
            return new ArrayList<>();
        }
        
        String role = user.getUserRole();
        System.out.println("getPermissionList - userRole: " + role);
        
        List<String> list = new ArrayList<>();
        
        if (UserRoleEnum.ADMIN.getValue().equals(role)) {
            list.add("user:*");
            list.add("company:*");
            list.add("position:*");
            list.add("statistics:*");
            list.add("recommendation:*");
            list.add("userCollection:*");
            list.add("userApply:*");
            list.add("userBehavior:*");
        } else if (UserRoleEnum.HR.getValue().equals(role)) {
            list.add("position:*");
            list.add("company:*");
            list.add("position:get");
            list.add("company:get");
            list.add("user:get");
            list.add("statistics:get");
            list.add("recommendation:get");
            list.add("userCollection:get");
            list.add("userApply:get");
            list.add("userBehavior:get");
        } else if (UserRoleEnum.STUDENT.getValue().equals(role)) {
            list.add("position:get");
            list.add("company:get");
            list.add("user:get");
            list.add("statistics:get");
            list.add("recommendation:get");
            list.add("userCollection:get");
            list.add("userApply:get");
            list.add("userBehavior:get");
        }
        
        System.out.println("getPermissionList - permissions: " + list);
        return list;
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
