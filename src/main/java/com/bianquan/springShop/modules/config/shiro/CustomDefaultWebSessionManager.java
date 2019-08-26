package com.bianquan.springShop.modules.config.shiro;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class CustomDefaultWebSessionManager extends DefaultWebSessionManager {

    private static final String AUTH_TOKEN = "token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /** 
     * 获取session id
     * 前后端分离将从请求头中获取jsesssionid
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 从请求头中获取token
        String token = WebUtils.toHttp(request).getHeader(AUTH_TOKEN);
        // 判断是否有值
        System.out.println("token is : " + token);
        // 若header获取不到token则尝试从cookie中获取
        if (StringUtils.isEmpty(token)){
            //如果没有携带id参数则按照父类的方式在cookie进行获取sessionId
            return super.getSessionId(request, response);
        } else {
            //请求头中如果有 authToken, 则其值为sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            //sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        }
    }
}
