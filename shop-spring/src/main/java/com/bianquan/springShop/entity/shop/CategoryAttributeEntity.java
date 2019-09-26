package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_category_attributes")
public class CategoryAttributeEntity implements Serializable {
    private static final long serialVersionUID = 6420269152156540428L;

    @TableId
    private Integer id;

    private Long categoryId;

    private String params;

    private Long createTime;

    private Long updateTime;
}
