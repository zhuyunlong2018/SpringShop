package com.bianquan.springShop.modules.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.shop.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    /**
     * 通过手机号码获取用户
     * @param mobile
     * @return
     */
    UserEntity queryByMobile(String mobile);
}
