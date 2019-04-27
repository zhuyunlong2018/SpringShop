package com.bianquan.springShop.modules.shop.controller;

import com.bianquan.springShop.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/categories")
@Api("商品分类")
public class Categories {

    @GetMapping("list")
    @ApiOperation("获取所有分类")
    public R list() {

        return R.ok();
    }

}
