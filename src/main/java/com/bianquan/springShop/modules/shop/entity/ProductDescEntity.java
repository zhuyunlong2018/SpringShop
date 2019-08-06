package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sp_product_desc")
public class ProductDescEntity implements Serializable {

    //商品ID
    @TableId
    private Long productId;

    //商品详情描述
    private String productDesc;

    private Date createTime;

    private Date updateTime;
}
