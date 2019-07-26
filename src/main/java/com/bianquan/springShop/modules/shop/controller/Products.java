package com.bianquan.springShop.modules.shop.controller;


import com.bianquan.springShop.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop/products")
@Api("商品")
public class Products {

    @GetMapping("getProducts")
    @ApiOperation("获取商品信息")
    public Response getProducts() {
        return Response.ok();
    }

}
