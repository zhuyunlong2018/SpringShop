package com.bianquan.springShop.modules.admin.controller;


import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.admin.dao.AdminDao;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import com.bianquan.springShop.modules.admin.serivice.AdminService;
import com.bianquan.springShop.modules.config.shiro.JwtToken;
import com.bianquan.springShop.modules.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@RestController
@RequestMapping("/admin")
@Api(tags = "后台登录操作")
public class AdminLogin {
    private static final Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Response login(String username, String password, HttpServletRequest request) {
        //TODO 验证码验证
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        System.out.println("UsernamePasswordToken:");
        System.out.println("hashCode:" + token.hashCode());
        System.out.println("Principal:" + token.getPrincipal());
        System.out.println("Credentials:" + String.valueOf((char[]) token.getCredentials()));
        System.out.println("host:" + token.getHost());
        System.out.println("Username:" + token.getUsername());
        System.out.println("Password:" + String.valueOf(token.getPassword()));
//        User user = userService.login(username, password);

        try {

            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            log.error("对用户[{}]进行登录验证,验证未通过,用户不存在", username);
            token.clear();
            return Response.error("UnknownAccountException");
        } catch (IncorrectCredentialsException ice) {
            return Response.error("验证不通过");
        } catch (LockedAccountException lae) {
            log.error("对用户[{}]进行登录验证,验证未通过,账户已锁定", username);
            token.clear();
            return Response.error("LockedAccountException");
        } catch (ExcessiveAttemptsException e) {
            log.error("对用户[{}]进行登录验证,验证未通过,错误次数过多", username);
            token.clear();
            return Response.error("ExcessiveAttemptsException");
        } catch (AuthenticationException e) {
            log.error("对用户[{}]进行登录验证,验证未通过,堆栈轨迹如下", username, e);
            token.clear();
            return Response.error("AuthenticationException");
        }
        //更新登录信息
//        user.setIp(HttpTool.getIpAddr(request));
//        user.setOs(HttpTool.getOs(request));
//        user.setUpdateUserId(user.getId());
//        user.setUpdateTime(CommonTool.getTimestamp());

        //设置session时间
        //SecurityUtils.getSubject().getSession().setTimeout(1000*60*30);

        //token信息
        Subject subject = SecurityUtils.getSubject();
//        Serializable tokenId = subject.getSession().getId();
        String tokenBack = jwtUtil.generateToken(8);
//        AuthenticationToken token= new JwtToken(strToken);


        return Response.ok(tokenBack);
    }

    @PostMapping("/doLogin")
    @ApiOperation("登录接口")
    public Response doLogin(String username, String password) {
        String jwt = adminService.login(username, password);
        System.out.println("login jwt:" + jwt);
//        Subject subject = SecurityUtils.getSubject();
//        AuthenticationToken token= new JwtToken(jwt);

//        subject.login(token);

        return Response.ok(jwt);
    }

    @PostMapping("/register")
    @ApiOperation("注册管理员")
    public Response register(String username, String password) {
        //生成盐（部分，需要存入数据库中）
        String random = new SecureRandomNumberGenerator().nextBytes().toHex();
        //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
        String mdPassword = new Md5Hash(password, random, 3).toString();
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName(username);
        adminEntity.setPassword(mdPassword);
        adminEntity.setSalt(random);
        return Response.ok(adminDao.insert(adminEntity));
    }

    @GetMapping("/test")
    @ApiOperation("shiroTest")
    @RequiresUser
    public Response test() {
        return Response.ok("test");
    }

    @PutMapping("/logout")
    @ApiOperation("登出")
    public Response logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return Response.ok();
    }
}
