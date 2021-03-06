package com.bianquan.springShop.elasticsearch;


import com.bianquan.springShop.entity.shop.ProductEntity;
import com.bianquan.springShop.web.shop.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(locations = {"classpath:application*.yml"})
public class ElasticSearch {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insert() {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("hello-product");

        System.out.println("test_hello");
        productRepository.save(productEntity);
    }
}
