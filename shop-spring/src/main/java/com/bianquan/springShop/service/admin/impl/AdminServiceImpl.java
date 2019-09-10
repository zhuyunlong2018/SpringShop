package com.bianquan.springShop.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.dao.admin.AdminDao;
import com.bianquan.springShop.entity.admin.AdminEntity;
import com.bianquan.springShop.service.admin.AdminService;
import com.bianquan.springShop.modules.utils.JwtUtil;
import com.google.gson.Gson;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao, AdminEntity> implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public Map<String, Object> login(String username, String password) {
        AdminEntity admin = queryByName(username);
        if (null == admin) {
            throw new RRException("账号不存在");
        }
        //密码校验
        String mdPassword = new Md5Hash(password, admin.getSalt(), 3).toString();
        if (!mdPassword.equals(admin.getPassword())) {
            throw new RRException("密码错误");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", admin.getId());
        map.put("name", admin.getName());
        String token = jwtUtil.generateToken(new Gson().toJson(map));
        map.put("token", token);
        return map;
    }

    @Override
    public AdminEntity queryByName(String name) {
        QWrapper<AdminEntity> queryWrapper = new QWrapper<>();
        queryWrapper.eq(AdminEntity.NAME, name);
        return getOne(queryWrapper);
    }

    @Override
    public Map<String, Object> queryPageWithRole(int currentPage, int pageSize) {
        int currentIndex = (currentPage - 1) * pageSize;
        List<AdminEntity> list = adminDao.fetchWithRolesByPage(currentIndex, pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", count());
        map.put("list", list);
        return map;
    }

    @Override
    public Boolean addAdmin(AdminEntity adminEntity) {
        //校验是否重名
        AdminEntity checkName = queryByName(adminEntity.getName());
        if (checkName != null) {
            throw new RRException(adminEntity.getName() + "已存在");
        }
        //生成盐（部分，需要存入数据库中）
        String random = new SecureRandomNumberGenerator().nextBytes().toHex();
        //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
        String mdPassword = new Md5Hash("123456", random, 3).toString();
        adminEntity.setPassword(mdPassword);
        adminEntity.setSalt(random);
        boolean result = save(adminEntity);
        if (result || adminEntity.getRoles() != null) {
            //添加角色关系
            adminDao.addRoleRelations(adminEntity.getId(), adminEntity.getSelectRoles());
        }
        return result;
    }

    @Override
    public Boolean editAdmin(AdminEntity adminEntity) {
        //检验是否重名
        QWrapper<AdminEntity> wrapper = new QWrapper<>();
        wrapper.eq(AdminEntity.NAME, adminEntity.getName())
                .ne(AdminEntity.ID, adminEntity.getId());
        if (count(wrapper) > 0) {
            throw new RRException(adminEntity.getName() + "已被使用");
        }
        boolean result = updateById(adminEntity);
        if (result) {
            //删除原来角色关系，填写新的角色关系
            adminDao.deleteAllRoleRelations(adminEntity.getId());
            adminDao.addRoleRelations(adminEntity.getId(), adminEntity.getSelectRoles());
            //TODO 角色关联信息变动，将导致管理员权限变动，要清除redis缓存的权限信息
        }
        return result;
    }

    @Override
    public Boolean deleteAdmin(int id) {
        boolean result = removeById(id);
        if (result) {
            //删除角色关系
            adminDao.deleteAllRoleRelations(id);
            //TODO 角色关联信息变动，将导致管理员权限变动，要清除redis缓存的权限信息
        }
        return result;
    }
}
