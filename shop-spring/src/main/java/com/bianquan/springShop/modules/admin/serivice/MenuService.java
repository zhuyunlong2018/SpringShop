package com.bianquan.springShop.modules.admin.serivice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.admin.entity.MenuEntity;

import java.util.List;

public interface MenuService extends IService<MenuEntity> {

    /**
     * 获取用户的前端路由列表
     * @param list
     * @return
     */
    List<MenuEntity> getRoutes(List<String> list);
}
