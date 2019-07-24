package com.bianquan.springShop.modules.shop.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.shop.annotation.Login;
import com.bianquan.springShop.modules.shop.annotation.LoginUser;
import com.bianquan.springShop.modules.shop.entity.UserEntity;
import com.bianquan.springShop.modules.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 */
@RestController
@RequestMapping("/shop/user")
@Api(description="用户操作")
public class User {

    @Autowired
    private UserService userService;

    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public Response userInfo(@LoginUser UserEntity user) {

        return Response.ok(user);
    }


}
