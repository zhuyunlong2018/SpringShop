package com.bianquan.springShop.modules.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.modules.shop.dao.CategoriesDao;
import com.bianquan.springShop.modules.shop.entity.CategoriesEntity;
import com.bianquan.springShop.modules.shop.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CategoriesService")
public class CategoriesServiceImpl extends ServiceImpl<CategoriesDao, CategoriesEntity> implements CategoriesService {

    @Autowired
    private CategoriesDao categoriesDao;

    @Override
    public List<CategoriesEntity> queryWithChildren() {
        return categoriesDao.listWithChildren();
    }
}
