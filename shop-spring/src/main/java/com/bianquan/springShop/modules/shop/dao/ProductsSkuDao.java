package com.bianquan.springShop.modules.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.shop.entity.ProductsSkuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsSkuDao extends BaseMapper<ProductsSkuEntity> {

    /**
     * 根据商品ID获取所关联SKU列表
     * @param id
     * @return
     */
    List<ProductsSkuEntity> fetchListByProductId(Long id);
}
