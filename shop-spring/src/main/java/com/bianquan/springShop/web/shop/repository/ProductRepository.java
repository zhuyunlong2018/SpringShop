package com.bianquan.springShop.web.shop.repository;

import com.bianquan.springShop.entity.shop.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends ElasticsearchRepository<ProductEntity, Long>,
        PagingAndSortingRepository<ProductEntity, Long> {


}
