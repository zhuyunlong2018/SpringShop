package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_shopping_cart")
public class ShoppingCartEntity implements Serializable {

    private static final long serialVersionUID = -3346689356108695362L;

    @TableId
    private Long id;

    //关联用户ID
    private Long userId;

    //关联产品skuID
    private Long productSkuId;

    //产品sku标题
    private String productSkuTitle;

    //添加时价格
    private Double addPrice;

    //添加数量
    private Integer number;

    //是否过期
    private Integer expired;

    //关联的商品sku
    @TableField(exist = false)
    private ProductsSkuEntity productSku;

    private Date createTime;

    private Date updateTime;
}
