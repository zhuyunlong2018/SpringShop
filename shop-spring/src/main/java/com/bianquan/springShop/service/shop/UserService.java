package com.bianquan.springShop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.shop.UserEntity;
import com.bianquan.springShop.modules.shop.form.LoginForm;

public interface UserService extends IService<UserEntity> {

    UserEntity queryByMobile(String mobile);

    /**
     * 用户登录
     * @param form
     * @return
     */
    long login(LoginForm form);
}
