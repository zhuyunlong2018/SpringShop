package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sp_product_attributes")
public class ProductAttributesEntity implements Serializable {

    @TableId
    private Long id;

    //商品ID
    private Long productId;

    //参数，json
    private String paramData;

    private Date createTime;

    private Date updateTime;

}
