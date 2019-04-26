package com.bianquan.springShop.modules.shop.dao;

import com.bianquan.springShop.modules.shop.entity.UserEntity;
import com.bianquan.springShop.modules.web.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByMobile(long mobile);
}
