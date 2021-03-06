package com.bianquan.springShop.web.admin.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.bianquan.springShop.service.admin.ImageClassService;
import com.bianquan.springShop.entity.admin.ImagesClassEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.exception.RRException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 图片分类表 前端控制器
 * @author zhuyunlong2018
 * @since 2019-09-21
 */
@Api(tags = "后台管理-图片分类")
@RestController
@RequestMapping("/admin/imagesClass")
public class ImagesClass {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImageClassService imageClassService;

    @GetMapping("/list")
    @RequiresPermissions("admin:images:list")
    @ApiOperation("图片分类数据")
    public Response findListByPage(){
        List<ImagesClassEntity> list = imageClassService.list();
        return Response.ok(list);
    }

    @PostMapping("/add")
    @RequiresPermissions("admin:imagesClass:add")
    @ApiOperation("新增数据")
    public Response add(@RequestBody ImagesClassEntity imagesClassEntity){

        boolean result = imageClassService.save(imagesClassEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok(imagesClassEntity);
    }

    @PutMapping("/edit")
    @RequiresPermissions("admin:imagesClass:edit")
    @ApiOperation("更新数据")
    public Response edit(@RequestBody ImagesClassEntity imagesClassEntity){

        boolean result = imageClassService.updateById(imagesClassEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(imagesClassEntity);
     }

    @DeleteMapping("/del")
    @RequiresPermissions("admin:imagesClass:del")
    @ApiOperation("删除数据")
    public Response del(@RequestParam("id") int id){
        boolean result = imageClassService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

}
