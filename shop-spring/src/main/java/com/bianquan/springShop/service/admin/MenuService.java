package com.bianquan.springShop.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.entity.admin.MenuEntity;

public interface MenuService extends IService<MenuEntity> {

    /**
     * 通过key删除菜单
     * @param key
     * @return
     */
    Boolean delByKey(String key);

}
