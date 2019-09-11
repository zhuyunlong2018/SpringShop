package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.UserEntity;
import com.bianquan.springShop.web.shop.form.LoginForm;

import java.util.List;

public interface UserService extends IService<UserEntity> {

    /**
     * 通过手机号检索用户
     * @param mobile
     * @return
     */
    UserEntity queryByMobile(String mobile);

    /**
     * 用户登录
     * @param form
     * @return
     */
    long login(LoginForm form);

    /**
     * 获取分页数据
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<UserEntity> getPage(int currentPage, int pageSize);
}
