package com.bianquan.springShop.modules.admin.controller;


import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.validator.Assert;
import com.bianquan.springShop.common.validator.ValidatorUtils;
import com.bianquan.springShop.modules.admin.dao.AdminDao;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import com.bianquan.springShop.modules.admin.serivice.AdminService;
import com.bianquan.springShop.modules.admin.form.LoginForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/admin")
@Api(tags = "后台登录操作")
public class AdminLogin extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AdminLogin.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    public Response login(@RequestBody LoginForm loginForm) {
        ValidatorUtils.validateEntity(loginForm);
        Map<String, Object> map = adminService.login(loginForm.getUsername(), loginForm.getPassword());
        return Response.ok(map);
    }

    @PostMapping("/register")
    @ApiOperation("注册管理员")
    public Response register(String username, String password) {
        Assert.isBlank(username, "用户名不能为空");
        Assert.isBlank(password, "密码不能为空");
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName(username);
        Boolean result = adminService.addAdmin(adminEntity);
        if (!result) {
            throw new RRException("注册失败");
        }
        return Response.ok();
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

    @GetMapping("/test")
    @ApiOperation("shiroTest")
    @RequiresUser
    public Response test() {
        return Response.ok("test");
    }

    @GetMapping("/test1")
    @ApiOperation("permissionsTest")
    @RequiresPermissions("test1")
    public Response test1() {

        return Response.ok(getUserId());
    }


    @GetMapping("/test2")
    @ApiOperation("permissionsTest2")
    @RequiresPermissions("test2")
    public Response test2() {

        return Response.ok(getUserId());
    }


}
