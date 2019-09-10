package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.CommonUtils;
import com.bianquan.springShop.common.utils.DateUtil;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.admin.MenuEntity;
import com.bianquan.springShop.service.admin.MenuService;
import com.bianquan.springShop.service.admin.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menus")
@Api(tags = "管理菜单操作")
public class Menus extends AbstractController {

    @Autowired
    MenuService menuService;

    @Autowired
    Permissions permissions;

    @GetMapping("/getUserRoutes")
    @ApiOperation("获取用户路由列表")
    public Response getUserRoutes() {
        //获取管理员关联角色的前端页面权限
        List<MenuEntity> routes = permissions.getUserRoutes(getUserId());
        return Response.ok(routes);
    }

    @GetMapping("/getMenus")
    @ApiOperation("获取后台所有菜单列表")
    @RequiresPermissions("admin:menus:getMenus")
    public Response getMenus() {
        return Response.ok(menuService.list());
    }

    @PostMapping("/add")
    @ApiOperation("添加一个菜单")
    @RequiresPermissions("admin:menus:add")
    public Response add(@RequestBody MenuEntity menuEntity) {
        //生成随机key
        menuEntity.setKey(DateUtil.timeStamp() + CommonUtils.randomStr(5));
        boolean result = menuService.save(menuEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok();
    }

    @PutMapping("/edit")
    @ApiOperation("编辑一个菜单")
    @RequiresPermissions("admin:menus:edit")
    public Response edit(@RequestBody MenuEntity menuEntity) {
        boolean result = menuService.updateById(menuEntity);
        //TODO 编辑的菜单如果修改code字段，将导致管理员权限变动，要清除redis缓存的权限信息
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok();
    }

    @DeleteMapping("/del")
    @ApiOperation("删除一个菜单")
    @RequiresPermissions("admin:menus:del")
    public Response del(@RequestParam(value = "key") String key) {
        Boolean result = menuService.delByKey(key);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }
}
