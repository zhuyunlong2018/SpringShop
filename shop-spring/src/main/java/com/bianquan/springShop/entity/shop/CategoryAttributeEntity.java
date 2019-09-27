package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@FieldNameConstants(prefix = "")
@ApiModel(value="CategoryAttributesEntity对象")
@TableName("sp_category_attributes")
public class CategoryAttributeEntity implements Serializable {
    private static final long serialVersionUID = 6420269152156540428L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = ",新增不传",example="1")
    private Long id;

    @ApiModelProperty(value = "商品类目ID",example="1")
    private Long categoryId;

    @ApiModelProperty(value = "参数数据，格式为json格式")
    private String params;

    @ApiModelProperty(value = "",example="1")
    private Integer createTime;

    @ApiModelProperty(value = "",example="1")
    private Integer updateTime;
}
