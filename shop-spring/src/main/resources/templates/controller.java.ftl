package ${package.Controller};


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bianquan.springShop.common.utils.Response;
import java.util.List;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!} 前端控制器
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@Api(tags = {"${table.comment!}"})
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
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
    @ApiOperation("查询分页数据")
    @GetMapping("/list")
    public Response findListByPage(@RequestParam(name = "page", defaultValue = "1") int pageNum,
                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        return Response.ok();
    }


    /**
     * 根据id查询
     */
    @ApiOperation("根据id查询数据")
    @RequestMapping("/getById")
    public Response getById(@RequestParam("pkid") String pkid){
       return Response.ok();
    }

    /**
     * 新增
     */
    @ApiOperation("新增数据")
    @PostMapping("/add")
    public Response add(@RequestBody ${entity} ${entity?uncap_first}){
        return Response.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/del")
    public Response delete(@RequestParam("ids") List<String> ids){
        return null;
    }

    /**
     * 修改
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public Response update(@RequestBody ${entity} ${entity?uncap_first}){
        return null;
     }

}
</#if>
