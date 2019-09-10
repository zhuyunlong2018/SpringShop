package com.bianquan.springShop.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.shop.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
}
