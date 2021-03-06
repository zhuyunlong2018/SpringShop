package com.bianquan.springShop.web.admin.config.shiro;


import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.utils.JwtUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义shiro过滤器
 */
public class AuthFilter extends BasicHttpAuthenticationFilter {

    private Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }
        return new JwtToken(token);
    }


    /**
     * 是否放行=>默认直接不放行，进入下一轮onAccessDenied进行处理
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * 拒绝处理=>上面的方法如果返回false,则接下来会执行这个方法,如果返回为true,则不会执行这个方法
     * 进行登录处理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, If-Modified-Since, token");
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            String json = new Gson().toJson(Response.error(HttpStatus.SC_UNAUTHORIZED, JwtUtil.HEADER_TOKEN +"不能为空"));
            httpResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }


    /**
     * 获取请求中的token,首先从请求头中获取,如果没有,则尝试从请求参数中获取
     *
     * @param request
     * @return
     */
    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.HEADER_TOKEN);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(JwtUtil.HEADER_TOKEN);
        }
        return token;
    }




    /**
     * 登录认证失败后处理，返回前端json
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, If-Modified-Since, token");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Response r = Response.error(4001, throwable.getMessage());
            String json = new Gson().toJson(r);
            httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);

            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

}
