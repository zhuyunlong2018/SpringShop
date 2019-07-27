package com.bianquan.springShop.modules.shop.controller;


import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.shop.entity.ProductEntity;
import com.bianquan.springShop.modules.shop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("shop/product")
@Api("商品")
public class Product {

    @Autowired
    private ProductService productService;

    @GetMapping("getProducts")
    @ApiOperation("获取商品信息")
    public Response getProducts() {
        List<ProductEntity> productList = productService.list();
        return Response.ok(productList);
    }

}
