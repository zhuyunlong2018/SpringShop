package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_product_attributes")
public class ProductAttributesEntity implements Serializable {

    private static final long serialVersionUID = 7622054474401365562L;

    @TableId
    private Long id;

    //商品ID
    private Long productId;

    //参数，json
    private String paramData;

    private Date createTime;

    private Date updateTime;

}
