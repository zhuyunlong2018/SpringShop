package com.bianquan.springShop.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.dao.admin.MenuDao;
import com.bianquan.springShop.entity.admin.MenuEntity;
import com.bianquan.springShop.service.admin.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    MenuDao menuDao;

    @Override
    public Boolean delByKey(String key) {
        QWrapper<MenuEntity> wrapper = new QWrapper<>();
        wrapper.eq(MenuEntity.PARENT_KEY, key);
        int count = count(wrapper);
        if (count > 0) {
            throw new RRException("请先解除与下级菜单关联再删除");
        }
        Integer delCount = menuDao.deleteByKey(key);
        return SqlHelper.delBool(delCount);
    }

}
