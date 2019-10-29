package com.bianquan.springShop.service.shop.impl;

import com.bianquan.springShop.dao.shop.ProductAttributesDao;
import com.bianquan.springShop.entity.shop.ProductAttributesEntity;
import com.bianquan.springShop.service.shop.ProductAttributesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品规格和商品的关系表 服务实现类
 * </p>
 *
 * @author zhuyunlong2018
 * @since 2019-10-29
 */
@Service
public class ProductAttributesServiceImpl extends ServiceImpl<ProductAttributesDao, ProductAttributesEntity> implements ProductAttributesService {

}
