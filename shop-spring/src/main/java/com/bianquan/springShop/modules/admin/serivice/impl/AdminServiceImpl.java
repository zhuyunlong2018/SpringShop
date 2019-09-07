package com.bianquan.springShop.modules.admin.serivice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.modules.admin.dao.AdminDao;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import com.bianquan.springShop.modules.admin.serivice.AdminService;
import com.bianquan.springShop.modules.utils.JwtUtil;
import com.google.gson.Gson;
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
        String token =  jwtUtil.generateToken(new Gson().toJson(map));
        map.put("token", token);
        return map;
    }

    @Override
    public Set<String> getUserPermissions(Integer id) {
        List<String> permsList;

        //系统管理员，拥有最高权限
//        if(id == Constant.SUPER_ADMIN){
//            List<SysMenuEntity> menuList = sysMenuDao.queryList(new HashMap<>());
//            permsList = new ArrayList<>(menuList.size());
//            for(SysMenuEntity menu : menuList){
//                permsList.add(menu.getPerms());
//            }
//        }else{
//            permsList = sysUserDao.queryAllPerms(userId);
//        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        permsSet.add("test1");
//        for(String perms : permsList){
//            if(StringUtils.isBlank(perms)){
//                continue;
//            }
//            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
//        }
        return permsSet;
    }

    @Override
    public AdminEntity queryAdmin(Integer id) {
        return new AdminEntity();
    }

    @Override
    public AdminEntity queryByName(String name) {
        QWrapper<AdminEntity> queryWrapper = new QWrapper<>();
        queryWrapper.eq(AdminEntity.NAME, name);
        return this.getOne(queryWrapper);
    }
}
