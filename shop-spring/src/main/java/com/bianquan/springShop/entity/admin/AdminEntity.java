package com.bianquan.springShop.entity.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bianquan.springShop.common.annotation.IsNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_admin")
public class AdminEntity implements Serializable {
    private static final long serialVersionUID = 8856671588431660612L;

    @TableId
    private Integer id;

    //管理员名
    @ApiModelProperty("管理员名称")
    @NotBlank(message = "管理员名不能为空")
    private String name;

    //密码
    @IsNull
    @JsonIgnore
    private String password;

    //密码盐
    @IsNull
    @JsonIgnore
    private String salt;

    private Date createTime;

    private Date updateTime;

    //管理员状态
    private Integer status;

    //描述
    @ApiModelProperty("管理员描述")
    @NotBlank(message = "管理员描述")
    private String description;

    //绑定角色
    @TableField(exist = false)
    private List<RoleEntity> roles;

    //前端编辑的绑定角色Ids
    @TableField(exist = false)
    private List<Integer> selectRoles;

}
