package com.bianquan.springShop.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sp_admin")
public class AdminEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户id
    @TableId
    private Long id;

    //用户名
    @ApiModelProperty("管理员名")
    private String name;

    //用户密码
    private String password;

    //用户状态
    private int status;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;
}
