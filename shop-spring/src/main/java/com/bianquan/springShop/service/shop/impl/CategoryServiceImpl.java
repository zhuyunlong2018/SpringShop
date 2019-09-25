package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.dao.shop.CategoryDao;
import com.bianquan.springShop.entity.shop.CategoryEntity;
import com.bianquan.springShop.service.shop.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoriesDao;

    @Override
    public List<CategoryEntity> queryWithChildren(Long pid) {
        return categoriesDao.listWithChildren(pid);
    }

    @Override
    public Map<String, Object> queryPageWithImage(int currentPage, int pageSize, int level, String title) {
        int currentIndex = (currentPage - 1) * pageSize;
        List<CategoryEntity> list = categoriesDao.fetchPageWithImage(currentIndex, pageSize, level, title);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", count());
        map.put("records", list);
        return map;
    }
}
