package com.bianquan.springShop.modules.config.shiro;

import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import com.bianquan.springShop.modules.admin.serivice.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("dfahfoasduser name" + userName);
        if (userName.equals("admin")) {

            List<String> permissions = new ArrayList<>();
            List<String> roles = new ArrayList<>();
            permissions.add("add");
            roles.add("superAdmin");
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRoles(roles);
            simpleAuthorizationInfo.addStringPermissions(permissions);
            return simpleAuthorizationInfo;
        } else {
            return null;
        }
    }

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //通过token获取用户账号
        String userName = (String) authenticationToken.getPrincipal();
        if (userName != null) {
            //数据库中请求用户信息
            AdminEntity adminEntity = adminService.queryByName(userName);
            String password = adminEntity.getPassword();
            System.out.println(password);
            System.out.println(adminEntity.getSalt());
            ByteSource salt = ByteSource.Util.bytes(adminEntity.getSalt());
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, password, getName());
            //设置盐，用来核对密码
            simpleAuthenticationInfo.setCredentialsSalt(salt);
            return simpleAuthenticationInfo;
        }
        return null;
    }


}
