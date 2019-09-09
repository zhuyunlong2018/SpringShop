package com.bianquan.springShop.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.admin.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<RoleEntity> {

    List<RoleEntity> getRolesWithMenusByUserId(Integer userId);

    Integer checkRoleIsUsed(int id);
}
