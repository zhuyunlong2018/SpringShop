package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sp_categories")
public class CategoriesEntity {
    private static final long serialVersionUID = 1L;

    //分类id
    @TableId
    private long id;

    //父级id
    private long pid;

    //分类标题
    private String title;

    //分类状态
    private int status;

    //分类描述
    private String description;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;
}
