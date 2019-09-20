package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.entity.shop.ProductEntity;
import com.bianquan.springShop.service.shop.ProductService;
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

/**
 * 商品表 前端控制器
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

    /**
     * 查询分页数据
     */
    @GetMapping("/list")
    @ApiOperation("查询分页数据")
    public Response findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int currentPage,
                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<ProductEntity> page = new Page<>(currentPage, pageSize);
        IPage<ProductEntity> list = productService.page(page);
        return Response.ok(list);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public Response add(@RequestBody ProductEntity productEntity){

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
    @ApiOperation("更新数据")
    public Response edit(@RequestBody ProductEntity productEntity){

        boolean result = productService.updateById(productEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(productEntity);
     }

    /**
     * 删除
     */
    @DeleteMapping("/del")
    @ApiOperation("删除数据")
    public Response del(@RequestParam("id") int id){
        boolean result = productService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

}
