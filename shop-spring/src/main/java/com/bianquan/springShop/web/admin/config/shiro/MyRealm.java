package com.bianquan.springShop.web.admin.config.shiro;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.bianquan.springShop.service.admin.Permissions;
import com.bianquan.springShop.common.utils.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private Permissions permissionsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //获取管理员信息
        String json = principalCollection.getPrimaryPrincipal().toString();
        Map map = new Gson().fromJson(json, Map.class);
        Double id = (Double) map.get("id");

        //获取管理员权限列表
        Set<String> permissions = permissionsService.getUserPermissions(id.intValue());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        String token = (String) authenticationToken.getPrincipal();

        if (ObjectUtils.isNull(token) || StringUtils.isBlank(token)) {
            throw new AuthenticationException(jwtUtil.getHeader() + "不能为空");
        }

        Claims claims = jwtUtil.getClaimByToken(token);

        return new SimpleAuthenticationInfo(claims.getSubject(), token, "my_realm");
    }


}
