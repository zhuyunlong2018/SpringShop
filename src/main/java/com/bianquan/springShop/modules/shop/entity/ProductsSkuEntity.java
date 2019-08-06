package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sp_products_sku")
public class ProductsSkuEntity implements Serializable {

    @TableId
    private Long id;

    //商品ID
    private Long productId;

    //sku描述
    private String description;

    //图片
    private String images;

    //sku价格
    private Double price;

    //sku库存
    private Integer stock;

    //sku参数
    private String attributes;
}
