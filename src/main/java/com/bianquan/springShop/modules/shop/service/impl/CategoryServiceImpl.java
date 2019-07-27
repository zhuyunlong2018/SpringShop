package com.bianquan.springShop.modules.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.modules.shop.dao.CategoryDao;
import com.bianquan.springShop.modules.shop.entity.CategoryEntity;
import com.bianquan.springShop.modules.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoriesDao;

    @Override
    public List<CategoryEntity> queryWithChildren(Long pid) {
        return categoriesDao.listWithChildren(pid);
    }
}
