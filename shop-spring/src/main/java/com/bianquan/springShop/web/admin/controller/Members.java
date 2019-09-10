package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.shop.UserEntity;
import com.bianquan.springShop.service.shop.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "后台管理-会员管理")
@RequestMapping("/admin/member")
public class Members {

    @Autowired
    private UserService userService;

    @GetMapping("/getList")
    @RequiresPermissions("admin:member:getList")
    @ApiOperation("分页获取会员列表")
    public Response getPage() {

        return Response.ok();
    }
}
