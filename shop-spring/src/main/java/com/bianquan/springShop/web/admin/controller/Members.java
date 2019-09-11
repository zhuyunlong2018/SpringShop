package com.bianquan.springShop.web.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.shop.UserEntity;
import com.bianquan.springShop.service.shop.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "后台管理-会员管理")
@RequestMapping("/admin/member")
public class Members {

    @Autowired
    private UserService userService;

    @GetMapping("/getList")
    @RequiresPermissions("admin:member:getList")
    @ApiOperation("分页获取会员列表")
    public Response getPage(@RequestParam("page") int currentPage, int pageSize) {
        IPage<UserEntity> page = userService.getPage(currentPage, pageSize);
        return Response.ok(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("admin:member:add")
    @ApiOperation("添加一名用户")
    public Response add() {

        return Response.ok();
    }

    @GetMapping("/edit")
    @RequiresPermissions("admin:member:edit")
    @ApiOperation("编辑一名会员")
    public Response edit() {

        return Response.ok();
    }

    @GetMapping("/del")
    @RequiresPermissions("admin:member:del")
    @ApiOperation("删除一名会员")
    public Response del() {

        return Response.ok();
    }
}
