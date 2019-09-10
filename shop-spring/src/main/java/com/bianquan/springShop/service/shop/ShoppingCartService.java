package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.ShoppingCartEntity;

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

    /**
     * 更新购物车产品数量
     * @param shoppingCartEntity
     */
    void changeNumber(ShoppingCartEntity shoppingCartEntity);

    /**
     * 删除购物车中的某个产品
     * @param shoppingCartEntity
     */
    void deleteOne(ShoppingCartEntity shoppingCartEntity);
}
