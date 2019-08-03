package com.bianquan.springShop.modules.shop.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.shop.annotation.Login;
import com.bianquan.springShop.modules.shop.annotation.LoginUser;
import com.bianquan.springShop.modules.shop.entity.ShoppingCartEntity;
import com.bianquan.springShop.modules.shop.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop/shoppingCart")
@Api("商品购物车")
public class ShoppingCart {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Login
    @GetMapping("/list")
    @ApiOperation("获取我的购物车列表")
    public Response fetchList(@LoginUser Long userId) {
        List<ShoppingCartEntity> list = shoppingCartService.fetchListWithProductSku(userId);
        return Response.ok(list);
    }

    @Login
    @PutMapping("/add")
    @ApiOperation("添加产品到购物车中")
    public Response add(
            @LoginUser Long userId,
            @RequestBody ShoppingCartEntity shoppingCartEntity
    ) {
        shoppingCartEntity.setUserId(userId);
        ShoppingCartEntity shoppingCart = shoppingCartService.addAndUpdate(shoppingCartEntity);
        return Response.ok(shoppingCart);
    }
}
