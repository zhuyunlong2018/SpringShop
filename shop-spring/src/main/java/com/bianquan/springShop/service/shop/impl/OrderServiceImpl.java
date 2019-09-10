package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.dao.shop.OrderDao;
import com.bianquan.springShop.entity.shop.OrderEntity;
import com.bianquan.springShop.service.shop.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;
}
