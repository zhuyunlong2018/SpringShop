package com.bianquan.springShop.modules.admin.controller;


import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.validator.Assert;
import com.bianquan.springShop.modules.admin.dao.AdminDao;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import com.bianquan.springShop.modules.admin.serivice.AdminService;
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



@RestController
@RequestMapping("/admin")
@Api(tags = "后台登录操作")
public class AdminLogin extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AdminLogin.class);

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    public Response login(String username, String password) {
        Assert.isBlank(username, "用户名不能为空");
        Assert.isBlank(password, "密码不能为空");
        String jwt = adminService.login(username, password);
        return Response.ok(jwt);
    }

    @PostMapping("/register")
    @ApiOperation("注册管理员")
    public Response register(String username, String password) {
        //TODO 修改为form表单，校验是否重名问题
        Assert.isBlank(username, "用户名不能为空");
        Assert.isBlank(password, "密码不能为空");
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
