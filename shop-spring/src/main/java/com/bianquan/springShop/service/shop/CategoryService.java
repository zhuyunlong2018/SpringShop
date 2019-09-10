package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.CategoryEntity;

import java.util.List;

public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 通过pid递归获取所有子集信息
     * @param pid
     * @return
     */
    List<CategoryEntity> queryWithChildren(Long pid);

}
