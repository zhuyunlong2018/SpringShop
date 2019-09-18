package ${package.Controller};


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
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
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!} 前端控制器
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!}")
@RestController
@RequestMapping("/admin/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};

    /**
     * 查询分页数据
     */
    @GetMapping("/list")
    @ApiOperation("查询分页数据")
    public Response findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int currentPage,
                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<${entity}> page = new Page<>(currentPage, pageSize);
        IPage<${entity}> list = ${(table.serviceName?substring(1))?uncap_first}.page(page);
        return Response.ok(list);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public Response add(@RequestBody ${entity} ${entity?uncap_first}){

        boolean result = ${(table.serviceName?substring(1))?uncap_first}.save(${entity?uncap_first});
        if (!result) {
            throw new RRException("添加失败");
        }
        return Response.ok(${entity?uncap_first});
    }

    /**
     * 修改
     */
    @PutMapping("/edit")
    @ApiOperation("更新数据")
    public Response edit(@RequestBody ${entity} ${entity?uncap_first}){

        boolean result = ${(table.serviceName?substring(1))?uncap_first}.updateById(${entity?uncap_first});
        if (!result) {
            throw new RRException("更新失败");
        }
        return Response.ok(${entity?uncap_first});
     }

    /**
     * 删除
     */
    @DeleteMapping("/del")
    @ApiOperation("删除数据")
    public Response del(@RequestParam("id") int id){
        boolean result = ${(table.serviceName?substring(1))?uncap_first}.removeById(id);
        if (!result) {
            throw new RRException("删除失败");
        }
        return Response.ok();
    }

}
</#if>
