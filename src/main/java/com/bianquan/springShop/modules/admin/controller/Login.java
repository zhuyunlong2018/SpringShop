package com.bianquan.springShop.modules.admin.controller;


import com.bianquan.springShop.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Api("后台登录接口")
public class Login {


    @PostMapping("login")
    @ApiOperation("登录")
    public R login() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", "fasdfhoapduiasdf");
        map.put("expire", 12366L);
        return R.ok(map);
    }
}
