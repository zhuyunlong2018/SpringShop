package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.ProductEntity;

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
