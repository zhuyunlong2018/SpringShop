package com.bianquan.springShop.modules.shop.service;

import com.bianquan.springShop.modules.shop.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserEntity queryObject(Long userId);

    List<UserEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(Long mobile, String password);

    void update(UserEntity user);

    void delete(Long userId);

    void deleteBatch(Long[] userIds);

    UserEntity queryByMobile(Long mobile);

    long login(Long mobile, String password);
}
