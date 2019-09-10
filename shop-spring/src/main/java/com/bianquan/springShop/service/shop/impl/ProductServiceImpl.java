package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.dao.shop.ProductDao;
import com.bianquan.springShop.entity.shop.ProductEntity;
import com.bianquan.springShop.service.shop.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao,ProductEntity> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductEntity> fetchList() {
        return productDao.fetchList();
    }

    @Override
    public ProductEntity fetchById(Long id) {
        return productDao.fetchById(id);
    }
}
