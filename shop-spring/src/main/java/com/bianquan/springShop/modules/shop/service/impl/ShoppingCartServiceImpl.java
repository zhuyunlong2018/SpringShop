package com.bianquan.springShop.modules.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.common.validator.Assert;
import com.bianquan.springShop.modules.shop.dao.ShoppingCartDao;
import com.bianquan.springShop.modules.shop.entity.ProductsSkuEntity;
import com.bianquan.springShop.modules.shop.entity.ShoppingCartEntity;
import com.bianquan.springShop.modules.shop.service.ProductsSkuService;
import com.bianquan.springShop.modules.shop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCartEntity> implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private ProductsSkuService productsSkuService;

    @Override
    public List<ShoppingCartEntity> fetchListWithProductSku(Long userId) {
        return shoppingCartDao.fetchListWithProductSku(userId);
    }

    @Override
    public ShoppingCartEntity addAndUpdate(ShoppingCartEntity shoppingCartEntity) {

        Assert.nonPositiveInteger(shoppingCartEntity.getNumber(), "数量必须为正整数");
        //检索该商品规格是否已在购物车中
        QWrapper<ShoppingCartEntity> queryWrapper = new QWrapper<>();
        queryWrapper.eq(ShoppingCartEntity.PRODUCT_SKU_ID, shoppingCartEntity.getProductSkuId())
                    .eq(ShoppingCartEntity.USER_ID, shoppingCartEntity.getUserId());
        ShoppingCartEntity shoppingCart = shoppingCartDao.selectOne(queryWrapper);

        Date date = new Date();
        if (null == shoppingCart) {
            //用户没有同规格的商品在购物车中
            ProductsSkuEntity productsSkuEntity = productsSkuService.getById(shoppingCartEntity.getProductSkuId());
            if (null == productsSkuEntity) {
                throw new RRException("未找到对应商品信息", 404);
            }
            shoppingCart = new ShoppingCartEntity();
            shoppingCart.setUserId(shoppingCartEntity.getUserId());
            shoppingCart.setProductSkuId(productsSkuEntity.getId());
            shoppingCart.setProductSkuTitle(productsSkuEntity.getDescription());
            shoppingCart.setAddPrice(productsSkuEntity.getPrice());
            shoppingCart.setNumber(shoppingCartEntity.getNumber());
            shoppingCart.setCreateTime(date);
            shoppingCart.setUpdateTime(date);
            shoppingCartDao.insert(shoppingCart);
        } else {
            //已经存在购物车中，更新数量
            shoppingCart.setNumber(shoppingCartEntity.getNumber() + shoppingCart.getNumber());
            shoppingCart.setUpdateTime(date);
            shoppingCartDao.updateById(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    public void changeNumber(ShoppingCartEntity shoppingCartEntity) {
        Assert.nonPositiveInteger(shoppingCartEntity.getNumber(), "数量必须为正整数");
        QWrapper<ShoppingCartEntity> queryWrapper = new QWrapper<>();
        queryWrapper.eq(ShoppingCartEntity.ID, shoppingCartEntity.getId())
                .eq(ShoppingCartEntity.USER_ID, shoppingCartEntity.getUserId());
        shoppingCartEntity.setUpdateTime(new Date());
        shoppingCartDao.update(shoppingCartEntity, queryWrapper);
    }

    @Override
    public void deleteOne(ShoppingCartEntity shoppingCartEntity) {
        QWrapper<ShoppingCartEntity> queryWrapper = new QWrapper<>();
        queryWrapper.eq(ShoppingCartEntity.ID, shoppingCartEntity.getId())
                .eq(ShoppingCartEntity.USER_ID, shoppingCartEntity.getUserId());
        shoppingCartDao.delete(queryWrapper);
    }
}
