package com.bianquan.springShop.web.admin.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.service.admin.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "后台管理-系统设置")
@RequestMapping("/admin/system")
public class SystemController extends AbstractController {

    @Autowired
    private SystemService systemService;


    @GetMapping("/refreshCache")
    @RequiresPermissions("admin:system:refreshCache")
    @ApiOperation("刷新系统缓存")
    public Response refreshCache() {
        systemService.refreshCache();
        return Response.ok();
    }

}
