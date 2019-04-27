package com.bianquan.springShop.modules.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.shop.entity.UserEntity;
import com.bianquan.springShop.modules.shop.form.LoginForm;

public interface UserService extends IService<UserEntity> {

    UserEntity queryByMobile(String mobile);

    long login(LoginForm form);
}
