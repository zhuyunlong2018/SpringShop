package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_product_desc")
public class ProductDescEntity implements Serializable {

    private static final long serialVersionUID = -3937519908308681466L;

    @TableId
    private Long id;

    private Long productId;

    //商品详情描述
    private String productDesc;

    private Date createTime;

    private Date updateTime;
}
