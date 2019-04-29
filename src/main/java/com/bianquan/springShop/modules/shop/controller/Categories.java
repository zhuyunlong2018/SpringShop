package com.bianquan.springShop.modules.shop.controller;

import com.bianquan.springShop.common.utils.R;
import com.bianquan.springShop.modules.shop.entity.CategoriesEntity;
import com.bianquan.springShop.modules.shop.service.CategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/categories")
@Api("商品分类")
public class Categories {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("list")
    @ApiOperation("获取所有分类")
    public R list() {

        List<CategoriesEntity> list = categoriesService.list();
        return R.ok().put("data", list);
    }

    @GetMapping("listWithChildren")
    @ApiOperation("按层级获取所有分类")
    public R listWithChildren() {

        List<CategoriesEntity> list = categoriesService.queryWithChildren();
        return R.ok().put("data", list);
    }

}
