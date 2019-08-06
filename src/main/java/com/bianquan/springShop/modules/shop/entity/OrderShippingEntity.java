package com.bianquan.springShop.modules.shop.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sp_order_shipping")
public class OrderShippingEntity implements Serializable {

    //订单ID
    private String orderId;

    //收货人姓名
    private String receiverName;

    //固定电话
    private String receiverPhone;

    //移动电话
    private String receiverMobile;

    //省份
    private String province;

    //城市
    private String city;

    //市、区、县
    private String district;

    //详细地址
    private String address;

    //邮政编码
    private String zipCode;

    private Date createTime;

    private Date updateTime;

}