package com.bianquan.springShop.modules.shop.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.modules.shop.annotation.Login;
import com.bianquan.springShop.modules.shop.annotation.LoginUser;
import com.bianquan.springShop.entity.shop.ShoppingCartEntity;
import com.bianquan.springShop.service.shop.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop/shoppingCart")
@Api(tags = "商品购物车")
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

    @Login
    @PutMapping("/changeNum")
    @ApiOperation("修改购物车某个商品数量")
    public Response changeNum(
            @LoginUser Long userId,
            @RequestBody ShoppingCartEntity shoppingCartEntity
    ) {
        shoppingCartEntity.setUserId(userId);
        shoppingCartService.changeNumber(shoppingCartEntity);
        return Response.ok();
    }

    @Login
    @DeleteMapping("/delete")
    @ApiOperation("删除购物车中的某个商品")
    public Response delete(
            @LoginUser Long userId,
            @RequestBody ShoppingCartEntity shoppingCartEntity
    ) {
        shoppingCartEntity.setUserId(userId);
        shoppingCartService.deleteOne(shoppingCartEntity);
        return Response.ok();
    }
}
