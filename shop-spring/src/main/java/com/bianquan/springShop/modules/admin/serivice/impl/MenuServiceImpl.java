package com.bianquan.springShop.modules.admin.serivice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.modules.admin.dao.MenuDao;
import com.bianquan.springShop.modules.admin.entity.MenuEntity;
import com.bianquan.springShop.modules.admin.serivice.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Override
    public List<MenuEntity> getRoutes(List<String> list) {
        return menuDao.getRoutes(list);
    }

}
