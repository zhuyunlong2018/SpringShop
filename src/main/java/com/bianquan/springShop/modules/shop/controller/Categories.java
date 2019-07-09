package com.bianquan.springShop.modules.shop.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bianquan.springShop.common.utils.R;
import com.bianquan.springShop.modules.shop.entity.CategoriesEntity;
import com.bianquan.springShop.modules.shop.service.CategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/categories")
@Api("商品分类")
public class Categories {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/rootList")
    @ApiOperation("获取所有根分类")
    public R rootList() {
        QueryWrapper<CategoriesEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", 0)
                    .eq("status", 1);
        List<CategoriesEntity> list = categoriesService.list(queryWrapper);
        return R.ok().put("data", list);
    }

    @GetMapping("/listWithChildren/{pid}")
    @ApiOperation("按层级获取指定菜单的所有下级")
    public R listWithChildren(@PathVariable("pid") Long pid) {
        List<CategoriesEntity> list = categoriesService.queryWithChildren(pid);
        return R.ok().put("data", list);
    }

}
