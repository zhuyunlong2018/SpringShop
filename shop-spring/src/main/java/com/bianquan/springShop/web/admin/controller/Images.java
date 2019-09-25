package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.utils.DateUtil;
import com.bianquan.springShop.common.utils.FileUtil;
import com.bianquan.springShop.common.utils.QWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @Autowired
    private FileUtil fileUtil;

    @GetMapping("/list")
    @RequiresPermissions("admin:images:list")
    @ApiOperation("查询分页数据")
    public Response findListByPage(
            @RequestParam(name = "pageNum", defaultValue = "1") int currentPage,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(name = "searchKey", defaultValue = "") String searchKey,
            @RequestParam(name = "classification", defaultValue = "0") int classification
    ){
        Page<ImageEntity> page = new Page<>(currentPage, pageSize);
        QWrapper<ImageEntity> wrapper = new QWrapper<>();
        wrapper.like(!"".equals(searchKey), ImageEntity.KEYWORDS, searchKey)
                .eq(classification > 0, ImageEntity.CLASSIFICATION, classification)
                .orderByDesc(ImageEntity.ID);
        IPage<ImageEntity> list = imageService.page(page, wrapper);
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
    @PostMapping("/upload")
    @RequiresPermissions("admin:images:upload")
    @ApiOperation("上传图片")
    public Response upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("keywords") String keywords,
            @RequestParam("classification") int classification
    ) {
        ImageEntity img = new ImageEntity();
        if (!fileUtil.upload(file, img)){
            throw new RRException("上传失败");
        }
        img.setKeywords(keywords);
        img.setTitle(file.getOriginalFilename());
        img.setSize(file.getSize());
        img.setOrigin(1);
        img.setClassification(classification);
        img.setCreateTime(DateUtil.time());
        img.setUpdateTime(DateUtil.time());

        boolean result = imageService.save(img);
        if (!result) {
            throw new RRException("图片信息保存失败");
        }
        return Response.ok(img);
    }

}
