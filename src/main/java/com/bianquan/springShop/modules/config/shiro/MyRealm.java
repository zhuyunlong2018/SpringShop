package com.bianquan.springShop.modules.config.shiro;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import com.bianquan.springShop.modules.admin.serivice.AdminService;
import com.bianquan.springShop.modules.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        String token = (String)authenticationToken.getPrincipal();

        System.out.println("token: " + token);

        if (ObjectUtils.isNull(token) || StringUtils.isBlank(token)) {
            throw new AuthenticationException(jwtUtil.getHeader()+"不能为空");
        }

        Claims claims = jwtUtil.getClaimByToken(token);

        if (ObjectUtils.isNull(claims)) {
            throw new AuthenticationException(jwtUtil.getHeader()+"无效");
        }

        if (jwtUtil.isTokenExpired(claims.getExpiration())) {
            throw new AuthenticationException(jwtUtil.getHeader()+"token过期");
        }

        System.out.println("now token is ok" +  getName());
        return new SimpleAuthenticationInfo(token, token, "my_realm");




        //通过token获取用户账号
//        String userName = (String) authenticationToken.getPrincipal();
//        System.out.println(userName);
//        if (userName != null) {
//            //数据库中请求用户信息
//            AdminEntity adminEntity = adminService.queryByName(userName);
//            String password = adminEntity.getPassword();
//            ByteSource salt = ByteSource.Util.bytes(adminEntity.getSalt());
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, password, getName());
//            //设置盐，用来核对密码
//            simpleAuthenticationInfo.setCredentialsSalt(salt);
//            return simpleAuthenticationInfo;
//        }
//        return null;
    }


}
