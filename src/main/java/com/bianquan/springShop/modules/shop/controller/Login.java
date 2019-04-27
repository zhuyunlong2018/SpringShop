package com.bianquan.springShop.modules.shop.controller;

import com.bianquan.springShop.common.utils.R;
import com.bianquan.springShop.common.validator.Assert;
import com.bianquan.springShop.common.validator.ValidatorUtils;
import com.bianquan.springShop.modules.shop.form.LoginForm;
import com.bianquan.springShop.modules.shop.service.UserService;
import com.bianquan.springShop.modules.utils.JwtUtils;
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
@Api("SHOP登录接口")
public class Login {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form) {

        ValidatorUtils.validateEntity(form);

        //用户登录
        long userId = userService.login(form);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }

    @GetMapping("test")
    public R test() {
        return R.ok("dfdf");
    }
}
