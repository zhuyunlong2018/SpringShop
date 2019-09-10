package com.bianquan.springShop.web.shop.controller;

import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.shop.CategoryEntity;
import com.bianquan.springShop.service.shop.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/category")
@Api(tags = "商品分类")
public class Category {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/rootList")
    @ApiOperation("获取所有根分类")
    public Response rootList() {
        QWrapper<CategoryEntity> queryWrapper = new QWrapper<>();
        queryWrapper.eq(CategoryEntity.PID, 0)
                    .eq(CategoryEntity.STATUS, 1);
        List<CategoryEntity> list = categoryService.list(queryWrapper);
        return Response.ok(list);
    }

    @GetMapping("/listWithChildren")
    @ApiOperation("按层级获取指定菜单的所有下级")
    public Response listWithChildren(@RequestParam("id") Long pid) {
        List<CategoryEntity> list = categoryService.queryWithChildren(pid);
        return Response.ok(list);
    }

    @GetMapping("/fetchList")
    @ApiOperation("获取所有列表")
    public Response fetchList() {
        List<CategoryEntity> list = categoryService.list();
        return Response.ok(list);
    }

}
