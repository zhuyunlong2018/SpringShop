package com.bianquan.springShop.modules.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.shop.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
