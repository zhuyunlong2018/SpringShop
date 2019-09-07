package com.bianquan.springShop.modules.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.validator.Assert;
import com.bianquan.springShop.modules.shop.dao.UserDao;
import com.bianquan.springShop.modules.shop.entity.UserEntity;
import com.bianquan.springShop.modules.shop.form.LoginForm;
import com.bianquan.springShop.modules.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity queryByMobile(String mobile) {
        return userDao.queryByMobile(mobile);
    }

    @Override
    public long login(LoginForm form) {
        UserEntity user = queryByMobile(form.getMobile());
        Assert.isNull(user, "手机号错误");

        byte[] pwd = (form.getPassword()+ user.getUserPwdSalt()).getBytes();
        if (!user.getUserPwd().equals(DigestUtils.md5DigestAsHex(pwd))) {
            throw new RRException("密码错误");
        }
        return user.getUserId();
    }


}