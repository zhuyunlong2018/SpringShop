package com.bianquan.springShop.modules.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.shop.entity.CategoriesEntity;
import com.bianquan.springShop.modules.shop.service.CategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/categories")
@Api("商品分类")
public class Categories {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/rootList")
    @ApiOperation("获取所有根分类")
    public Response rootList() {
        QueryWrapper<CategoriesEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", 0)
                    .eq("status", 1);
        List<CategoriesEntity> list = categoriesService.list(queryWrapper);
        return Response.ok(list);
    }

    @GetMapping("/listWithChildren")
    @ApiOperation("按层级获取指定菜单的所有下级")
    public Response listWithChildren(@RequestParam("id") Long pid) {
        List<CategoriesEntity> list = categoriesService.queryWithChildren(pid);
        return Response.ok(list);
    }

}
