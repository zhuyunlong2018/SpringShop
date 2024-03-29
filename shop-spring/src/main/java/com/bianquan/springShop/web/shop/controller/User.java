package com.bianquan.springShop.web.shop.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.web.shop.annotation.Login;
import com.bianquan.springShop.web.shop.annotation.LoginUser;
import com.bianquan.springShop.entity.shop.UserEntity;
import com.bianquan.springShop.service.shop.UserService;
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
@Api(tags = "web商城-用户操作")
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
