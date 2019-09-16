package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.BrandEntity;

public interface BrandService extends IService<BrandEntity> {

    /**
     * 获取分页列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<BrandEntity> page(int currentPage, int pageSize);
}
