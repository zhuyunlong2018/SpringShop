package com.bianquan.springShop.modules.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.shop.entity.CategoriesEntity;

import java.util.List;

public interface CategoriesService extends IService<CategoriesEntity> {

    List<CategoriesEntity> queryWithChildren();

}
