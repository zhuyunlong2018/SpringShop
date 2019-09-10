package com.bianquan.springShop.modules.admin.controller;


import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.CommonUtils;
import com.bianquan.springShop.common.utils.DateUtil;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.admin.entity.MenuEntity;
import com.bianquan.springShop.modules.admin.serivice.MenuService;
import com.bianquan.springShop.modules.admin.serivice.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    RoleService roleService;

    @GetMapping("/getUserRoutes")
    @ApiOperation("获取用户路由列表")
    public Response getUserRoutes() {

        //获取管理员关联角色的前端页面权限
        List<String> permission = roleService.getPermissionByAdminId(getUserId());
        if (permission.isEmpty()) {
            throw new RRException("您没有相关权限");
        }
        List<MenuEntity> routes = menuService.getRoutes(permission);
        return Response.ok(routes);
    }

    @GetMapping("/getMenus")
    @ApiOperation("获取后台所有菜单列表")
    public Response getMenus() {
        return Response.ok(menuService.list());
    }

    @PostMapping("/add")
    @ApiOperation("添加一个菜单")
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
    public Response edit(@RequestBody MenuEntity menuEntity) {
        boolean result = menuService.updateById(menuEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok();
    }

    @DeleteMapping("/del")
    @ApiOperation("删除一个菜单")
    public Response del(@RequestParam(value = "key") String key) {
        Boolean result = menuService.delByKey(key);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }
}
