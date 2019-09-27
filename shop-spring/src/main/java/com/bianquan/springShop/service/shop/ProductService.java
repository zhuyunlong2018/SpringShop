package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.ProductEntity;

import java.util.List;
import java.util.Map;

public interface ProductService extends IService<ProductEntity> {

    /**
     * 获取产品列表
     * @return
     */
    public List<ProductEntity> fetchList();

    /**
     * 通过ID获取产品信息
     * @param id
     * @return
     */
    public ProductEntity fetchById(Long id);

    /**
     * 获取包含图片、分类等数据的商品分页
     * @param currentPage
     * @param pageSize
     * @return
     */
    Map<String, Object> queryPageWithRelations(int currentPage, int pageSize, long categoryId, String title);
}
