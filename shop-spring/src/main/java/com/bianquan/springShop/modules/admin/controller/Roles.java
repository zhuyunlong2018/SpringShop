package com.bianquan.springShop.modules.admin.controller;


import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.admin.entity.RoleEntity;
import com.bianquan.springShop.modules.admin.serivice.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public Response getRoles() {
        List<RoleEntity> list = roleService.list();
        return Response.ok(list);
    }

    @PostMapping("/add")
    @ApiOperation("添加角色")
    public Response add(@RequestBody RoleEntity roleEntity) {
        Boolean result = roleService.saveRole(roleEntity);
        if (!result) {
            throw new RRException("添加角色失败");
        }
        return Response.ok(roleEntity);
    }

    @PutMapping("/edit")
    @ApiOperation("编辑角色")
    public Response edit(@RequestBody RoleEntity roleEntity) {
        Boolean result = roleService.saveRole(roleEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(roleEntity);
    }

    @DeleteMapping("/del")
    @ApiOperation("删除角色")
    public Response del(@RequestParam int id) {
        Boolean result = roleService.deleteRole(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }
}
