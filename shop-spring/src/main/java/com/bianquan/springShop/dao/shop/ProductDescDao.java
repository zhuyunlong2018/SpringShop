package com.bianquan.springShop.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.shop.ProductDescEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品描述表 Mapper 接口
 * </p>
 *
 * @author zhuyunlong2018
 * @since 2019-10-29
 */
@Mapper
public interface ProductDescDao extends BaseMapper<ProductDescEntity> {

}
