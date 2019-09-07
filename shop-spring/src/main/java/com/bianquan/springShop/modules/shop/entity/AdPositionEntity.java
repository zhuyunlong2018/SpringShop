package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_ad_position")
public class AdPositionEntity implements Serializable {
    private static final long serialVersionUID = 4925163437871463524L;

    @TableId
    private Integer id;

    //名称
    private String name;

    //key标识符
    private String key;
}
