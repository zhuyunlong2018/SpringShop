package com.bianquan.springShop.modules.shop.service.impl;

import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.validator.Assert;
import com.bianquan.springShop.modules.shop.dao.UserDao;
import com.bianquan.springShop.modules.shop.entity.UserEntity;
import com.bianquan.springShop.modules.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity queryObject(Long userId) {
        return userDao.queryObject(userId);
    }

    @Override
    public List<UserEntity> queryList(Map<String, Object> map) {
        return userDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return userDao.queryTotal(map);
    }

    @Override
    public void save(Long mobile, String password) {
        UserEntity user = new UserEntity();
        user.setUserMobile(mobile);
        userDao.save(user);
    }

    @Override
    public void update(UserEntity user) {
        userDao.update(user);
    }

    @Override
    public void delete(Long userId) {
        userDao.delete(userId);
    }

    @Override
    public void deleteBatch(Long[] userIds) {
        userDao.deleteBatch(userIds);
    }

    @Override
    public UserEntity queryByMobile(Long mobile) {
        return userDao.queryByMobile(mobile);
    }

    @Override
    public long login(Long mobile, String password) {
        UserEntity user = queryByMobile(mobile);
        Assert.isNull(user, "手机号错误");
        byte[] pwd = (password + user.getUserPwdSalt()).getBytes();
        if (!user.getUserPwd().equals(DigestUtils.md5DigestAsHex(pwd))) {
            throw new RRException("密码错误");
        }
        return user.getUserId();
    }

}
