package com.bianquan.springShop.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_admin")
public class AdminEntity implements Serializable {
    private static final long serialVersionUID = 8856671588431660612L;

    @TableId
    private Integer id;

    //管理员名
    private String name;

    //密码
    private String password;

    //密码盐
    private String salt;

    private Date createTime;

    private Date updateTime;

    //管理员状态
    private Integer status;

}
