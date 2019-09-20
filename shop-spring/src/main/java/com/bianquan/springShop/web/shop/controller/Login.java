package com.bianquan.springShop.web.shop.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.validator.ValidatorUtils;
import com.bianquan.springShop.web.shop.form.LoginForm;
import com.bianquan.springShop.service.shop.UserService;
import com.bianquan.springShop.common.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 商城授权登录
 */
@RestController
@RequestMapping("/shop")
@Api(tags = "web商城-登录接口")
public class Login {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtils;

    @PostMapping("login")
    @ApiOperation("登录")
    public Response login(@RequestBody LoginForm form) {

        ValidatorUtils.validateEntity(form);

        //用户登录
        long userId = userService.login(form);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return Response.ok(map);
    }

    @GetMapping("test")
    public Response test() {
        return Response.ok("dfdf");
    }
}
