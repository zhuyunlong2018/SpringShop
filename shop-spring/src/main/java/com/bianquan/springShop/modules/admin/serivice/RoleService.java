package com.bianquan.springShop.modules.admin.serivice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.admin.entity.RoleEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface RoleService extends IService<RoleEntity> {

    /**
     * 通过用户ID获取用户角色
     * @param userId
     * @return
     */
    List<String> getRolesByUserId(Integer userId);


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
}
