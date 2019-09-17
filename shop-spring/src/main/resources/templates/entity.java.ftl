package ${package.Entity};

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * ${table.comment!} 实体类
 * @author ${author}
 * @since ${date}
 */
 @Data
 @FieldNameConstants(prefix = "")
 @ApiModel(value="${entity}对象")
 @TableName("${table.name}")
public class ${table.entityName} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list table.fields as field>
<#--主键策略-->
<#if field.keyFlag>
    <#if field.keyIdentityFlag>
    @TableId(value = "${field.name}", type = IdType.AUTO)
    <#elseif (idType??)>
    @TableId(value = "${field.name}", type = IdType.${idType})
    <#elseif (field.convert)>
    @TableId("${field.name}")
    <#else>
    @TableId
    </#if>
</#if>
<#--swagger2字段api-->
<#if ("!field.comment" != "")>
    <#if (swagger2)>
        <#if (field.propertyType == "Long" || field.propertyType == "Integer")>
            <#if (field.keyFlag)>
    @ApiModelProperty(value = "${field.comment},新增不传",example="1")
            <#else>
    @ApiModelProperty(value = "${field.comment}",example="1")
            </#if>
        <#else>
    @ApiModelProperty(value = "${field.comment}")
        </#if>
    </#if>
</#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
}