package com.bianquan.springShop.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Data
@TableName("tv_user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户id
    @TableId
    private Long userId;

    //用户名
    @ApiModelProperty("用户名")
    private String userName;

    //用户密码
    private String userPwd;

    //用户邮箱
    private String userEmail;

    //用户状态
    private Integer userStatus;

    //密码md5加密盐
    private String userPwdSalt;

    //简介
    private String introduction;

    //用户手机
    private String userMobile;

    //用户头像
    private String userAvatar;

    //用户等级
    private Integer userLevel;

    //性别
    private Integer gender;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

}
