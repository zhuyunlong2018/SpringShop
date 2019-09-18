package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_categories")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 6401730510323415538L;

    @ApiModelProperty(value = "分类id")
    @TableId
    private Long id;

    //父级id
    private Long pid;

    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的。
    private List<CategoryEntity> children;

    //分类标题
    private String title;

    //分类状态
    private Integer status;

    //分类描述
    private String description;

    //分类级别，共三个级别，1位顶级分类
    @TableField("`level`")
    private Integer level;

    //图标
    private String img;

    //排序
    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;
}
