package com.bianquan.springShop.modules.shop.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_order_product")
public class OrderProductEntity implements Serializable {

    private static final long serialVersionUID = 8898086405931282497L;

    @TableId
    private Long id;

    //商品ID
    private Long productId;

    //订单号
    private String orderId;

    //购买数量
    private Integer num;

    //商品标题
    private String title;

    //商品单价
    private Double price;

    //商品总额
    private Double totalFee;

    //商品图片地址
    private String picPath;
}
