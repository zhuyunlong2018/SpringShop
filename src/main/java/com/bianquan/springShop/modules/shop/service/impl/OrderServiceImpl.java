package com.bianquan.springShop.modules.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.modules.shop.dao.OrderDao;
import com.bianquan.springShop.modules.shop.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> {

    @Autowired
    private OrderDao orderDao;
}
