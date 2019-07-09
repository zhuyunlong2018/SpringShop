package com.bianquan.springShop.modules.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.shop.entity.CategoriesEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoriesDao extends BaseMapper<CategoriesEntity> {

    List<CategoriesEntity> listWithChildren(Long pid);
}