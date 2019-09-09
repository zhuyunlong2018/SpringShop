package com.bianquan.springShop.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao extends BaseMapper<AdminEntity> {

    /**
     * 分页获取管理员并携带角色信息
     * @param currIndex
     * @param pageSize
     * @return
     */
    List<AdminEntity> fetchWithRolesByPage(int currIndex, int pageSize);

    /**
     * 删除管理员角色关联关系
     * @param adminId
     * @return
     */
    Integer deleteAllRoleRelations(int adminId);

    /**
     * 添加管理员角色关联关系
     * @param adminEntity
     * @return
     */
    Integer addRoleRelations(AdminEntity adminEntity);
}
