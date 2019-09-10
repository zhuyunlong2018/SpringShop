package com.bianquan.springShop.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.admin.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<RoleEntity> {

    /**
     * 通过用户ID 获取关联的橘色列表
     * @param userId
     * @return
     */
    List<RoleEntity> getRolesWithMenusByUserId(Integer userId);

    /**
     * 检查角色是否被管理员关联了
     * @param id
     * @return
     */
    Integer checkRoleIsUsed(int id);
}
