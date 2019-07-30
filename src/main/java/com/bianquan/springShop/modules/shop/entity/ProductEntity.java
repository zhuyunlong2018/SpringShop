package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sp_products")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String title;

    private String sellPoint;

    private String image;

    private Long categoryId;

    private Integer status;
}
