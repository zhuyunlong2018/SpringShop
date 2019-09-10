package com.bianquan.springShop.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.admin.MenuEntity;
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

    /**
     * 通过key删除菜单
     * @param key
     * @return
     */
    int deleteByKey(String key);

    /**
     * 根据用户拥有的keys获取为功能类型的菜单集合
     * @param keys
     * @return
     */
    List<MenuEntity> getHadCodeByKeys(List<String> keys);
}
