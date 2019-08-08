package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_products")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 5650088466120913205L;

    @TableId
    private Long id;

    //标题
    private String title;

    //商品卖点
    private String sellPoint;

    //商品主图
    private String image;

    //所属分类
    private Long categoryId;

    //商品状态
    private Integer status;

    //商品详情
    @TableField(exist = false)
    private ProductDescEntity desc;

    //商品参数
    @TableField(exist = false)
    private ProductAttributesEntity attributes;

    //sku参数
    @TableField(exist = false)
    private List<ProductsSkuEntity> sku;

    private Date createTime;

    private Date updateTime;
}
