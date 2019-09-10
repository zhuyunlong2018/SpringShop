package com.bianquan.springShop.modules.admin.serivice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.admin.entity.RoleEntity;

import java.util.List;

public interface RoleService extends IService<RoleEntity> {

    /**
     * 通过用户ID获取用户关联角色绑定的菜单权限
     * @param adminId
     * @return
     */
    List<String> getPermissionByAdminId(Integer adminId);


    /**
     * 添加或保存角色
     * @param roleEntity
     * @return
     */
    Boolean saveRole(RoleEntity roleEntity);

    /**
     * 删除一名角色
     * @param id
     * @return
     */
    Boolean deleteRole(int id);

    /**
     * 通过管理员ID获取关联角色列表
     * @param id
     * @return
     */
    List<RoleEntity> getRolesByAdminId(int id);
}
