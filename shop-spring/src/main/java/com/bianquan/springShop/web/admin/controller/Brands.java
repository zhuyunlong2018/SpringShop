package com.bianquan.springShop.web.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.shop.BrandEntity;
import com.bianquan.springShop.service.shop.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "后台管理-品牌管理")
@RequestMapping("/admin/brands")
public class Brands extends AbstractController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    @RequiresPermissions("admin:brands:list")
    @ApiOperation("分页获取品牌列表")
    public Response getPage(@RequestParam("pageNum") int currentPage, @RequestParam("pageSize") int pageSize) {
        IPage<BrandEntity> page = brandService.page(currentPage, pageSize);
        return Response.ok(page);
    }

    @PostMapping("/add")
    @RequiresPermissions("admin:brands:add")
    @ApiOperation("添加品牌")
    public Response add(@RequestBody BrandEntity brandEntity) {
        boolean result = brandService.save(brandEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok(brandEntity);
    }

    @PutMapping("/edit")
    @RequiresPermissions("admin:brands:edit")
    @ApiOperation("编辑品牌")
    public Response edit(@RequestBody BrandEntity brandEntity) {
        boolean result = brandService.updateById(brandEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(brandEntity);
    }

    @DeleteMapping("/del")
    @RequiresPermissions("admin:brands:del")
    @ApiOperation("删除")
    public Response del(@RequestParam int id) {
        boolean result = brandService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

    @GetMapping("/all")
    @RequiresPermissions("admin:brands:all")
    @ApiOperation("获取所有品牌列表")
    public Response getAll() {
        List<BrandEntity> list = brandService.list();
        return Response.ok(list);
    }
}
