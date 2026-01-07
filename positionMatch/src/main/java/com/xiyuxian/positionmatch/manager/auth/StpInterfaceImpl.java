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
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        //todo 这里一共3类角色，admin,user,company_hr
        List<String> list = new ArrayList<String>();
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        // list.add("user.delete");
        list.add("art.*");
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
