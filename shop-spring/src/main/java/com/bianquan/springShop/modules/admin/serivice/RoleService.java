package com.bianquan.springShop.modules.admin.serivice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.admin.entity.RoleEntity;

import java.util.List;

public interface RoleService extends IService<RoleEntity> {

    /**
     * 通过用户ID获取用户角色
     * @param userId
     * @return
     */
    List<String> getRolesByUserId(Integer userId);
}
