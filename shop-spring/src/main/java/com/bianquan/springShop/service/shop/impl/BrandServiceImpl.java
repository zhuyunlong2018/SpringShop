package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.dao.shop.BrandDao;
import com.bianquan.springShop.entity.shop.BrandEntity;
import com.bianquan.springShop.service.shop.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Override
    public IPage<BrandEntity> page(int currentPage, int pageSize) {
        Page<BrandEntity> page = new Page<>(currentPage, pageSize);
        IPage<BrandEntity> list = page(page);
        return list;
    }
}
