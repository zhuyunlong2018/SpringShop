package com.bianquan.springShop.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.shop.ShoppingCartEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCartEntity> {

    List<ShoppingCartEntity> fetchListWithProductSku(Long userId);
}
