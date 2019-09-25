package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.CategoryEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 通过pid递归获取所有子集信息
     * @param pid
     * @return
     */
    List<CategoryEntity> queryWithChildren(Long pid);

    /**
     * 获取包含图片的分类数据
     * @param currentPage
     * @param pageSize
     * @return
     */
    Map<String, Object> queryPageWithImage(int currentPage, int pageSize, int level, String title);
}
