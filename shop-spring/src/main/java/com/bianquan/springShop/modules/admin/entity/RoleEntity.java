package com.bianquan.springShop.modules.admin.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

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
}