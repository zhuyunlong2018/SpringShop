package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.validator.Assert;
import com.bianquan.springShop.dao.shop.UserDao;
import com.bianquan.springShop.entity.shop.UserEntity;
import com.bianquan.springShop.web.shop.form.LoginForm;
import com.bianquan.springShop.service.shop.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

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

    @Override
    public IPage<UserEntity> getPage(int currentPage, int pageSize) {
        Page<UserEntity> page = new Page<>(currentPage, pageSize);
        IPage<UserEntity> list = page(page);
        return list;
    }


}
