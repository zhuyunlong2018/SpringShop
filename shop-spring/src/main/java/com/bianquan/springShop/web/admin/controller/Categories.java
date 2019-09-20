package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.entity.shop.CategoryEntity;
import com.bianquan.springShop.service.shop.CategoryService;
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

import java.util.List;

/**
 * 商品类目 前端控制器
 * @author zhuyunlong2018
 * @since 2019-09-18
 */
@RestController("admin_categories")
@Api(tags = "后台管理-商品类目")
@RequestMapping("/admin/categories")
public class Categories {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分页数据
     */
    @ApiOperation("查询分页数据")
    @GetMapping("/list")
    @RequiresPermissions("admin:categories:list")
    public Response findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int currentPage,
                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                   @RequestParam(name = "level", defaultValue = "0") int level,
                                   @RequestParam(name = "title", defaultValue = "") String title
    ){
        QWrapper<CategoryEntity> wrapper = new QWrapper<>();
        wrapper.eq(level>0 ,CategoryEntity.LEVEL, level)
                .like(!"".equals(title), CategoryEntity.TITLE, title);

        Page<CategoryEntity> page = new Page<>(currentPage, pageSize);
        IPage<CategoryEntity> list = categoryService.page(page, wrapper);
        return Response.ok(list);
    }

    /**
     * 新增
     */
    @ApiOperation("新增数据")
    @PostMapping("/add")
    @RequiresPermissions("admin:categories:add")
    public Response add(@RequestBody CategoryEntity categoriesEntity){

        boolean result = categoryService.save(categoriesEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok(categoriesEntity);
    }

    /**
     * 修改
     */
    @ApiOperation("更新数据")
    @PutMapping("/edit")
    @RequiresPermissions("admin:categories:edit")
    public Response edit(@RequestBody CategoryEntity categoriesEntity){

        boolean result = categoryService.updateById(categoriesEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(categoriesEntity);
     }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/del")
    @RequiresPermissions("admin:categories:del")
    public Response del(@RequestParam("id") int id){
        boolean result = categoryService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

    @ApiOperation("根据级别获取类目")
    @GetMapping("/listByLevels")
    @RequiresPermissions("admin:categories:list")
    public Response getListByLevel(@RequestParam("levels[]") List<Integer> levels) {
        QWrapper<CategoryEntity> wrapper = new QWrapper<>();
        wrapper.in(CategoryEntity.LEVEL, levels);
        List<CategoryEntity> list = categoryService.list(wrapper);
        return Response.ok(list);
    }

}
