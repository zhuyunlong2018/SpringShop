package com.bianquan.springShop.modules.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.shop.entity.ShoppingCartEntity;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCartEntity> {

    /**
     * 通过用户ID获取购物车列表并携带对应商品sku信息
     * @param userId
     * @return
     */
    List<ShoppingCartEntity> fetchListWithProductSku(Long userId);

    /**
     * 添加或更新购物车
     * @param shoppingCartEntity
     * @return
     */
    ShoppingCartEntity addAndUpdate(ShoppingCartEntity shoppingCartEntity);
}
