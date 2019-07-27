package com.bianquan.springShop.modules.shop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.shop.entity.ProductEntity;
import com.bianquan.springShop.modules.shop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shop/product")
@Api("商品")
public class Product {

    @Autowired
    private ProductService productService;

    @GetMapping("/fetchList")
    @ApiOperation("根据类型ID获取商品列表")
    public Response fetchList(
            @RequestParam("page") Integer currentPage,
            @RequestParam("size") Integer pageSize,
            @RequestParam("cid") Long cid
    ) {
        Page<ProductEntity> page = new Page<>(currentPage, pageSize);
        QueryWrapper<ProductEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", cid)
                .eq("status", 1);
        IPage<ProductEntity> list = productService.page(page, queryWrapper);
        return Response.ok(list);
    }

}
