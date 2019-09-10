package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_order")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = -1559661153504042431L;

    @TableId
    private Long id;

    //付款金额
    private Double payment;

    //支付类型
    private Integer paymentType;

    //邮费
    private Double postFee;

    //状态
    private Integer status;

    private Date createTime;

    private Date updateTime;

    //支付时间
    private Date paymentTime;

    //发货时间
    private Date consignTime;

    //交易完成时间
    private Date endTime;

    //交易关闭时间
    private Date closeTime;

    //物流名称
    private String shippingName;

    //物流单号
    private String shippingCode;

    //用户ID
    private Long userId;

    //买家留言
    private String buyerMessage;

    //买家昵称
    private String buyerNick;

    //买家是否已评价
    private Integer buyerRate;
}
