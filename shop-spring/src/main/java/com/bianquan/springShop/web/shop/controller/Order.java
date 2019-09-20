package com.bianquan.springShop.web.shop.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.service.shop.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/order")
@Api(tags = "web商城-商品订单")
public class Order {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    @ApiOperation("添加订单")
    public Response add() {

        return Response.ok();
    }
}
