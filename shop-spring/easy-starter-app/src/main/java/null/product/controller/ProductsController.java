package null.product.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import null.product.service.IProductsService;
import null.product.entity.ProductsEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bianquan.springShop.common.utils.Response;
import com.bianquan.springShop.common.exception.RRException;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品表 前端控制器
 * @author zhuyunlong2018
 * @since 2019-09-16
 */
@Api(tags = "商品表")
@RestController
@RequestMapping("/product/products-entity")
public class ProductsController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IProductsService productsService;

    /**
     * 查询分页数据
     */
    @ApiOperation("查询分页数据")
    @GetMapping("/list")
    public Response findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int currentPage,
                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<ProductsEntity> page = new Page<>(currentPage, pageSize);
        IPage<ProductsEntity> list = productsService.page(page);
        return Response.ok(list);
    }

    /**
     * 新增
     */
    @ApiOperation("新增数据")
    @PostMapping("/add")
    public Response add(@RequestBody ProductsEntity productsEntity){

        boolean result = productsService.save(productsEntity);
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok(productsEntity);
    }

    /**
     * 修改
     */
    @ApiOperation("更新数据")
    @PutMapping("/edit")
    public Response edit(@RequestBody ProductsEntity productsEntity){

        boolean result = productsService.updateById(productsEntity);
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(productsEntity);
     }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/del")
    public Response del(@RequestParam("id") int id){
        boolean result = productsService.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

}
