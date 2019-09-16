package com.bianquan.springShop.entity.shop;



import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_brands")
public class BrandEntity implements Serializable {

    @TableId
    private Integer id;

    private String name;

    private String description;
}
