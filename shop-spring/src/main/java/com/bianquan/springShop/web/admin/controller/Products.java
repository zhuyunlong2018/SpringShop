package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.entity.shop.ProductEntity;
import com.bianquan.springShop.service.shop.ProductAttributesService;
import com.bianquan.springShop.service.shop.ProductDescService;
import com.bianquan.springShop.service.shop.ProductService;
import com.bianquan.springShop.service.shop.ProductsSkuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.exception.RRException;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品表 前端控制器
 *
 * @author zhuyunlong2018
 * @since 2019-09-19
 */
@Api(tags = "后台管理-商品列表")
@RestController
@RequestMapping("/admin/products")
public class Products extends AbstractController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductsSkuService productsSkuService;

    @Autowired
    private ProductDescService productDescService;

    @Autowired
    private ProductAttributesService productAttributesService;

    /**
     * 查询分页数据
     */
    @GetMapping("/list")
    @ApiOperation("查询分页数据")
    @RequiresPermissions("admin:products:list")
    public Response findListByPage(
            @RequestParam(name = "pageNum", defaultValue = "1") int currentPage,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(name = "categoryId", defaultValue = "0") long categoryId,
            @RequestParam(name = "title", defaultValue = "") String title
    ) {
        Map<String, Object> result = productService.queryPageWithRelations(currentPage, pageSize, categoryId, title);
        return Response.ok(result);
    }

    @GetMapping("/fetchById")
    @ApiOperation("根据ID查询商品sku、属性、详情数据")
    @RequiresPermissions("admin:products:list")
    public Response findById(@RequestParam(name = "id", defaultValue = "0") long id) {
        ProductEntity product = productService.fetchById(id);
        return Response.ok(product);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    @RequiresPermissions("admin:products:add")
    public Response add(@RequestBody ProductEntity productEntity) {

        boolean result = productService.save(productEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok(productEntity);
    }

    /**
     * 修改
     */
    @PutMapping("/edit")
    @RequiresPermissions("admin:products:edit")
    @ApiOperation("更新数据")
    public Response edit(@RequestBody ProductEntity productEntity) {
        //保存商品基础信息
        boolean result = productService.updateById(productEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        //更新sku列表
        productsSkuService.saveOrUpdateBatch(productEntity.getSku());

        //保存商品属性
        productAttributesService.saveOrUpdate(productEntity.getAttributes());
        //保存商品详情
        productDescService.saveOrUpdate(productEntity.getDesc());
        
        return Response.ok(productEntity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/del")
    @RequiresPermissions("admin:products:del")
    @ApiOperation("删除数据")
    public Response del(@RequestParam("id") int id) {
        boolean result = productService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

}
