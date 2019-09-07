package com.bianquan.springShop.modules.shop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.shop.entity.CategoryEntity;
import com.bianquan.springShop.modules.shop.entity.ProductEntity;

import java.util.List;

public interface ProductService extends IService<ProductEntity> {

    /**
     * 获取产品列表
     * @return
     */
    public List<ProductEntity> fetchList();

    /**
     * 通过ID获取产品信息
     * @param id
     * @return
     */
    public ProductEntity fetchById(Long id);
}
