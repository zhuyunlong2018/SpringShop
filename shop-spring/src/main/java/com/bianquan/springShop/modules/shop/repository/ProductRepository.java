package com.bianquan.springShop.modules.shop.repository;

import com.bianquan.springShop.modules.shop.entity.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends ElasticsearchRepository<ProductEntity, Long>,
        PagingAndSortingRepository<ProductEntity, Long> {


}