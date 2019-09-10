package com.bianquan.springShop.entity.admin;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_roles")
public class RoleEntity {

    @TableId
    private Integer id;

    //名称
    private String name;

    //描述
    private String description;

    //权限记录
    private String permissions;

    @TableField(exist = false)
    private List<String> keys;

}
