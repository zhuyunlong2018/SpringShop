package com.bianquan.springShop.modules.admin.serivice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.bianquan.springShop.common.exception.RRException;
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
    public List<String> getPermissionByAdminId(Integer adminId) {
        List<RoleEntity> roleList = roleDao.getRolesWithMenusByUserId(adminId);
        List<String> permissions = new ArrayList<>();
        List<String> list;
        for (RoleEntity role : roleList) {
            list = Arrays.asList(role.getPermissions().split(","));
            permissions.addAll(list);
        }
        return permissions;
    }

    @Override
    public Boolean saveRole(RoleEntity roleEntity) {
        String permission = "";
        for (String key: roleEntity.getKeys()) {
            if (!"".equals(key)) {
                permission += key + ",";
            }
        }
        roleEntity.setPermissions(permission);
        boolean result;
        if (null != roleEntity.getId()) {
            result = updateById(roleEntity);
        } else {
            result = save(roleEntity);
        }
        return result;
    }

    @Override
    public Boolean deleteRole(int id) {
        Integer check = roleDao.checkRoleIsUsed(id);
        if (SqlHelper.retCount(check) > 0) {
            throw new RRException("角色已绑定用户，请先取消关联");
        }
        return removeById(id);
    }

    @Override
    public List<RoleEntity> getRolesByAdminId(int id) {
        return roleDao.getRolesWithMenusByUserId(id);
    }
}
