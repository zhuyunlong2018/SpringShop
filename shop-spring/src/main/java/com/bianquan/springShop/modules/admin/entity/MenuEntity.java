package com.bianquan.springShop.modules.admin.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_menus")
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 7279150456504598260L;

    @ApiModelProperty("id")
    @TableId
    private Integer id;

    //菜单标识key
    @ApiModelProperty("菜单标识key")
    @TableField("`key`")
    private String key;

    //上级标识
    private String parentKey;

    //本地化描述
    @TableField("`local`")
    private String local;

    //描述
    @TableField("`text`")
    private String text;

    //跳转页面类别
    @TableField("`target`")
    private String target;

    //图标
    private String icon;

    //前端路由
    private String path;

    //跳转地址
    private String url;

    //排序
    @TableField("`order`")
    private Integer order;

    //类别，功能或菜单
    private Integer type;

    //后端权限编码
    private String code;
}
