package com.bianquan.springShop.modules.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.shop.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {
    public List<ProductEntity> fetchList();
}
