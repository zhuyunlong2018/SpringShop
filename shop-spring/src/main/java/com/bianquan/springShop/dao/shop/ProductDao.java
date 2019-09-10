package com.bianquan.springShop.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.shop.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {

    /**
     * 获取所有列表
     * @return
     */
    List<ProductEntity> fetchList();

    /**
     * 通过ID获取商品详细信息
     * @param id
     * @return
     */
    ProductEntity fetchById(Long id);
}