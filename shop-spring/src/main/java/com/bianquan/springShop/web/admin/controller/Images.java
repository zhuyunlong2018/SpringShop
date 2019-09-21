package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.utils.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.bianquan.springShop.service.admin.ImageService;
import com.bianquan.springShop.entity.admin.ImageEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.exception.RRException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 图片存储管理 前端控制器
 * @author zhuyunlong2018
 * @since 2019-09-21
 */
@Api(tags = "后台管理-图片存储管理")
@RestController
@RequestMapping("/admin/images")
public class Images {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImageService imageService;

    @GetMapping("/list")
    @ApiOperation("查询分页数据")
    public Response findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int currentPage,
                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<ImageEntity> page = new Page<>(currentPage, pageSize);
        IPage<ImageEntity> list = imageService.page(page);
        return Response.ok(list);
    }

    @PostMapping("/add")
    @ApiOperation("新增数据")
    public Response add(@RequestBody ImageEntity imagesEntity){

        boolean result = imageService.save(imagesEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok(imagesEntity);
    }

    @PutMapping("/edit")
    @ApiOperation("更新数据")
    public Response edit(@RequestBody ImageEntity imagesEntity){

        boolean result = imageService.updateById(imagesEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(imagesEntity);
     }

    @DeleteMapping("/del")
    @ApiOperation("删除数据")
    public Response del(@RequestParam("id") int id){
        boolean result = imageService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

    /**
     *
     * @param file 要上传的文件
     * @return
     */
    @PostMapping("fileUpload")
    public String upload(@RequestParam("fileName") MultipartFile file, Map<String, Object> map){

        // 要上传的目标文件存放路径
        String localPath = "E:/Develop/Files/Photos";
        // 上传成功或者失败的提示
        String msg = "";

        if (FileUtil.upload(file, localPath, file.getOriginalFilename())){
            // 上传成功，给出页面提示
            msg = "上传成功！";
        }else {
            msg = "上传失败！";

        }

        // 显示图片
        map.put("msg", msg);
        map.put("fileName", file.getOriginalFilename());

        return "forward:/test";
    }

}
