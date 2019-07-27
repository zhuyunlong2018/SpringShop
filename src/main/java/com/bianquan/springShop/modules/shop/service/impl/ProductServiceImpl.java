package com.bianquan.springShop.modules.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.modules.shop.dao.ProductDao;
import com.bianquan.springShop.modules.shop.entity.ProductEntity;
import com.bianquan.springShop.modules.shop.service.ProductService;
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
}
