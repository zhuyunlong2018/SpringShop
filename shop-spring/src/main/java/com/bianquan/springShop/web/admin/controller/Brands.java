package com.bianquan.springShop.web.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.entity.shop.BrandEntity;
import com.bianquan.springShop.service.shop.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "后台管理-品牌管理")
@RequestMapping("/admin/brands")
public class Brands extends AbstractController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    @ApiOperation("获取品牌列表")
    public Response getPage(@RequestParam("page") int currentPage, int pageSize) {
        IPage<BrandEntity> page = brandService.page(currentPage, pageSize);
        return Response.ok(page);
    }

    public Response save() {
        return Response.ok();
    }

}
