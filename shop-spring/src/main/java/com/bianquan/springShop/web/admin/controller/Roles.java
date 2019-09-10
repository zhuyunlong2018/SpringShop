package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.admin.RoleEntity;
import com.bianquan.springShop.service.admin.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
@Api(tags = "管理后台-角色操作")
public class Roles extends AbstractController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoles")
    @ApiOperation("获取角色列表")
    @RequiresPermissions("admin:roles:getRoles")
    public Response getRoles() {
        List<RoleEntity> list = roleService.list();
        return Response.ok(list);
    }

    @PostMapping("/add")
    @ApiOperation("添加角色")
    @RequiresPermissions("admin:roles:add")
    public Response add(@RequestBody RoleEntity roleEntity) {
        Boolean result = roleService.saveRole(roleEntity);
        if (!result) {
            throw new RRException("添加角色失败");
        }
        return Response.ok(roleEntity);
    }

    @PutMapping("/edit")
    @ApiOperation("编辑角色")
    @RequiresPermissions("admin:roles:edit")
    public Response edit(@RequestBody RoleEntity roleEntity) {
        Boolean result = roleService.saveRole(roleEntity);
        //TODO 编辑角色信息，将导致绑定的管理员权限变动，要清除redis缓存的权限信息
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(roleEntity);
    }

    @DeleteMapping("/del")
    @ApiOperation("删除角色")
    @RequiresPermissions("admin:roles:del")
    public Response del(@RequestParam int id) {
        Boolean result = roleService.deleteRole(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }
}
