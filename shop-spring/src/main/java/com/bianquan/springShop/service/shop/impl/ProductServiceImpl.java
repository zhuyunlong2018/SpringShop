package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.dao.shop.ProductDao;
import com.bianquan.springShop.dao.shop.ProductsSkuDao;
import com.bianquan.springShop.entity.shop.ProductEntity;
import com.bianquan.springShop.service.shop.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao,ProductEntity> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductsSkuDao productsSkuDao;

    @Override
    public List<ProductEntity> fetchList() {
        return productDao.fetchList();
    }

    @Override
    public ProductEntity fetchById(Long id) {
        return productDao.fetchById(id);
    }

    @Override
    public Map<String, Object> queryPageWithRelations(int currentPage, int pageSize, long categoryId, String title) {
        int currentIndex = (currentPage - 1) * pageSize;
        List<ProductEntity> list = productDao.fetchPageWithRelations(currentIndex, pageSize, categoryId, title);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", count());
        map.put("records", list);
        return map;
    }

}
