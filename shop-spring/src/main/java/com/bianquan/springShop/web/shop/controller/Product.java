package com.bianquan.springShop.web.shop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.shop.ProductEntity;
import com.bianquan.springShop.web.shop.repository.ProductRepository;
import com.bianquan.springShop.service.shop.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shop/product")
@Api(tags = "web商城-商品信息")
public class Product {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/fetchPageByCategory")
    @ApiOperation("根据类型ID获取商品列表分页")
    public Response fetchPageByCategory(
            @RequestParam("page") Integer currentPage,
            @RequestParam("size") Integer pageSize,
            @RequestParam("categoryId") Long categoryId
    ) {
        Page<ProductEntity> page = new Page<>(currentPage, pageSize);
        QWrapper<ProductEntity> queryWrapper = new QWrapper<>();
        queryWrapper.eq(ProductEntity.CATEGORY_ID, categoryId)
                .eq(ProductEntity.STATUS, 1);
        IPage<ProductEntity> list = productService.page(page, queryWrapper);
        return Response.ok(list);
    }

    @GetMapping("/fetchById")
    @ApiOperation("根据ID获取商品详情")
    public Response fetchById(@RequestParam("id") Long id) {
        return Response.ok(productService.fetchById(id));
    }

    @PostMapping("/test")
    @ApiOperation("test")
    public Response elasticTest(@RequestBody ProductEntity productEntity) {
        //TODO elasticsearch暂且搁置
        return Response.ok(productRepository.save(productEntity));
    }
}
