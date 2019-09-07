package com.bianquan.springShop.modules.admin.serivice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;

import java.util.Set;

public interface AdminService extends IService<AdminEntity> {

    String login(String username, String password);

    Set<String> getUserPermissions(Integer id);

    AdminEntity queryAdmin(Integer id);

    AdminEntity queryByName(String name);
}
