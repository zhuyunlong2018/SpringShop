package com.bianquan.springShop.web.admin.controller;


import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.entity.shop.CategoryAttributeEntity;
import com.bianquan.springShop.entity.shop.CategoryEntity;
import com.bianquan.springShop.service.shop.CategoryAttributeService;
import com.bianquan.springShop.service.shop.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.exception.RRException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 商品类目 前端控制器
 *
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

    @Autowired
    private CategoryAttributeService categoryAttributesService;

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
    ) {

        Map<String, Object> result = categoryService.queryPageWithImage(currentPage, pageSize, level, title);
        return Response.ok(result);
    }

    /**
     * 新增
     */
    @ApiOperation("新增数据")
    @PostMapping("/add")
    @RequiresPermissions("admin:categories:add")
    public Response add(@RequestBody CategoryEntity categoriesEntity) {

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
    public Response edit(@RequestBody CategoryEntity categoriesEntity) {

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
    public Response del(@RequestParam("id") int id) {
        boolean result = categoryService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

    @ApiOperation("根据级别获取类目")
    @GetMapping("/listByLevels")
    @RequiresPermissions("admin:categories:list")
    public Response getListByLevel(
            @RequestParam("levels[]") List<Integer> levels,
            @RequestParam(name = "pid", defaultValue = "-1") long pid
    ) {
        QWrapper<CategoryEntity> wrapper = new QWrapper<>();
        wrapper.in(!levels.isEmpty(),CategoryEntity.LEVEL, levels)
        .eq(pid >= 0, CategoryEntity.PID, pid);
        List<CategoryEntity> list = categoryService.list(wrapper);
        return Response.ok(list);
    }

    @PostMapping("/saveAttributes")
    @ApiOperation("保存分类参数组")
    public Response add(@RequestBody CategoryAttributeEntity categoryAttributesEntity) {

        boolean result = categoryAttributesService.saveOrUpdate(categoryAttributesEntity);
        if (!result) {
            throw new RRException("保存属性组失败");
        }
        return Response.ok(categoryAttributesEntity);
    }

    @ApiOperation("根据上级ID获取携带属性对象的列表")
    @GetMapping("/fetchWithParamsByPid")
    @RequiresPermissions("admin:categories:list")
    public Response fetchWithParamsByPid(@RequestParam("pid") Long pid) {
        List<CategoryEntity> list = categoryService.fetchWithParamsByPid(pid);
        return Response.ok(list);
    }

}
