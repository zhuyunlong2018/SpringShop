package com.bianquan.springShop.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.shop.ProductAttributesEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品规格和商品的关系表 Mapper 接口
 * </p>
 *
 * @author zhuyunlong2018
 * @since 2019-10-29
 */
@Mapper
public interface ProductAttributesDao extends BaseMapper<ProductAttributesEntity> {

}
