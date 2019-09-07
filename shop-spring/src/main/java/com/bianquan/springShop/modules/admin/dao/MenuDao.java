package com.bianquan.springShop.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.admin.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao extends BaseMapper<MenuEntity> {

    /**
     * 获取用户前端路由
     * @return
     */
    List<MenuEntity> getRoutes(List<String > list);

    /**
     * 获取所有列表
     * @return
     */
    List<MenuEntity> getList();
}
