package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.validator.ValidatorUtils;
import com.bianquan.springShop.entity.admin.AdminEntity;
import com.bianquan.springShop.entity.admin.RoleEntity;
import com.bianquan.springShop.service.admin.AdminService;
import com.bianquan.springShop.service.admin.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/admins")
@Api(tags = "后台管理-管理员操作")
public class Admins extends AbstractController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/getAdmins")
    @ApiOperation("获取管理员列表")
    @RequiresPermissions("admin:admins:getAdmins")
    public Response getAdmins(@RequestParam("page") Integer currentPage,
                              @RequestParam("pageSize") Integer pageSize) {
        Map<String, Object> result = adminService.queryPageWithRole(currentPage, pageSize);
        return Response.ok(result);
    }

    @PostMapping("/add")
    @ApiOperation("添加管理员")
    @RequiresPermissions("admin:admins:add")
    public Response add(@RequestBody AdminEntity adminEntity) {
        ValidatorUtils.validateEntity(adminEntity);
        Boolean result = adminService.addAdmin(adminEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        List<RoleEntity> roles = roleService.getRolesByAdminId(adminEntity.getId());
        adminEntity.setRoles(roles);
        return Response.ok(adminEntity);
    }

    @PutMapping("/edit")
    @ApiOperation("编辑管理员")
    @RequiresPermissions("admin:admins:edit")
    public Response edit(@RequestBody AdminEntity adminEntity) {
        ValidatorUtils.validateEntity(adminEntity);
        Boolean result = adminService.editAdmin(adminEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        List<RoleEntity> roles = roleService.getRolesByAdminId(adminEntity.getId());
        adminEntity.setRoles(roles);
        return Response.ok(adminEntity);
    }

    @DeleteMapping("/del")
    @ApiOperation("删除管理员")
    @RequiresPermissions("admin:admins:del")
    public Response del(@RequestParam int id) {
        Boolean result = adminService.deleteAdmin(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }
}
