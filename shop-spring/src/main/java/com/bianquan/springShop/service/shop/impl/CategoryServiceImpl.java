package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.dao.shop.CategoryDao;
import com.bianquan.springShop.entity.shop.CategoryEntity;
import com.bianquan.springShop.service.shop.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoriesDao;

    @Override
    public List<CategoryEntity> queryWithChildren(Long pid) {
        return categoriesDao.listWithChildren(pid);
    }
}
