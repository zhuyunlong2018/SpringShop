package com.bianquan.springShop.modules.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.shop.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<UserEntity> {

    UserEntity queryByMobile(Long mobile);

    long login(Long mobile, String password);
}
