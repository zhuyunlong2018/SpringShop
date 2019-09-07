package com.bianquan.springShop.modules.admin.serivice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.modules.admin.dao.RoleDao;
import com.bianquan.springShop.modules.admin.entity.RoleEntity;
import com.bianquan.springShop.modules.admin.serivice.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<String> getRolesByUserId(Integer userId) {
        List<RoleEntity> roleList = roleDao.getRolesWithMenusByUserId(userId);
        List<String> permissions = new ArrayList<>();
        List<String> list;
        for (RoleEntity role : roleList) {
            list = Arrays.asList(role.getPermissions().split(","));
            permissions.addAll(list);
        }
        return permissions;
    }
}
