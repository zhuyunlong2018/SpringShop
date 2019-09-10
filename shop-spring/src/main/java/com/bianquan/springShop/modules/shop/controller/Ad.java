package com.bianquan.springShop.modules.shop.controller;

import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.dao.shop.AdDao;
import com.bianquan.springShop.entity.shop.AdEntity;
import com.bianquan.springShop.service.shop.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/ad")
@Api(tags = "获取广告栏目")
public class Ad {

    @Autowired
    private AdService adService;

    @Autowired
    private AdDao adDao;

    @GetMapping("/homeBanner")
    @ApiOperation("获取首页轮播图")
    public Response homeBanner() {
        List<AdEntity> list = adDao.fetchListByKey("ad/homeBanner");
        return Response.ok(list);
    }
}
