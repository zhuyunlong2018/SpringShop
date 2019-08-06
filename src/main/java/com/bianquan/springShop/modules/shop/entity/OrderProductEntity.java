package com.bianquan.springShop.modules.shop.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sp_order_product")
public class OrderProductEntity implements Serializable {

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
