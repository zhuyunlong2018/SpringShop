package com.bianquan.springShop.modules.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.shop.entity.ProductEntity;

import java.util.List;

public interface ProductService extends IService<ProductEntity> {
    List<ProductEntity> queryList();
}
