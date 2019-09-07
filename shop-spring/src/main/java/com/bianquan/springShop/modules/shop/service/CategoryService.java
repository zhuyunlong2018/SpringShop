package com.bianquan.springShop.modules.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.shop.entity.CategoryEntity;

import java.util.List;

public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 通过pid递归获取所有子集信息
     * @param pid
     * @return
     */
    List<CategoryEntity> queryWithChildren(Long pid);

}
