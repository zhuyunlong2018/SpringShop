package com.bianquan.springShop.modules.shop.entity;

import java.io.Serializable;

/**
 * 用户表
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;

    private String userPwd;

    private String userEmail;

    private int userStatus;

    private String userPwdSalt;

    private String introduction;

    private Long userMobile;

    private String userAvatar;

    private int userLevel;

    private int gender;

    private Long createTime;

    private Long updateTime;

    private Long deleteTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserPwdSalt() {
        return userPwdSalt;
    }

    public void setUserPwdSalt(String userPwdSalt) {
        this.userPwdSalt = userPwdSalt;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(Long userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

}
